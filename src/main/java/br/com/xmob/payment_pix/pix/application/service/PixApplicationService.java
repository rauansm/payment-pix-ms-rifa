package br.com.xmob.payment_pix.pix.application.service;

import br.com.xmob.payment_pix.payment.application.api.PaymentRequest;
import br.com.xmob.payment_pix.payment.domain.Payment;
import br.com.xmob.payment_pix.payment.infra.PaymentRepository;
import br.com.xmob.payment_pix.pix.domain.PixExpiredRequest;
import br.com.xmob.payment_pix.pix.domain.PixRequest;
import br.com.xmob.payment_pix.pix.domain.PixResponse;
import br.com.xmob.payment_pix.pix.domain.PixStatusResponse;
import br.com.xmob.payment_pix.pix.infra.MercadoPagoFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class PixApplicationService implements PixService {
    private final MercadoPagoFeignClient mercadoPagoFeignClient;
    private final PaymentRepository paymentRepository;


    @Override
    public PixResponse createCharge(PaymentRequest paymentRequest) {
        log.info("[start] PixApplicationService - createCharge");
        log.debug("[PaymentRequest] {}", paymentRequest);
        PixResponse pixResponse = mercadoPagoFeignClient.createCharge(new PixRequest(paymentRequest));
        log.debug("[PixResponse] {}", pixResponse);
        log.info("[finish] PixApplicationService - createCharge");
        return pixResponse;
    }

    @CircuitBreaker(name = "searchPixStatus", fallbackMethod = "searchPixStatusFallback")
    @Override
    public PixStatusResponse searchPixPaymentStatus(Payment payment) {
        log.info("[start] PixApplicationService - searchPixPaymentStatus");
        log.debug("[Payment] {}", payment);
        PixStatusResponse statusPix = mercadoPagoFeignClient.searchPixPaymentStatus(payment.getId());
        log.debug("[PixStatusResponse] {}", statusPix);
        log.info("[finish] PixApplicationService - searchPixPaymentStatus");
        return statusPix;
    }
    @CircuitBreaker(name = "pixPaymentAsExpired")
    @Override
    public void markPixPaymentAsExpired(Long paymentId, String cancelled) {
        log.info("[start] PixApplicationService - markPixPaymentAsExpired");
        try {
        mercadoPagoFeignClient.markPixPaymentAsExpired(paymentId, new PixExpiredRequest(cancelled));
        } catch (Exception ex) {
            log.error("Falha ao marcar o pagamento Pix como expirado", ex);
            throw new RuntimeException("API indisponível no momento!");
        }
        log.info("[finish] PixApplicationService - markPixPaymentAsExpired");
    }

    public PixStatusResponse searchPixStatusFallback(Payment payment, Exception ex) {
        log.info("[start] PixApplicationService - searchPixStatusFallback");
        log.error("[Error] ", ex);
        if (payment.isIntegrated()){
            payment.markAsNotIntegrated();
            paymentRepository.save(payment);
        }
        log.info("[finish] PixApplicationService - searchPixStatusFallback");
        throw new RuntimeException("Error ao buscar status pix", ex);
    }
}
