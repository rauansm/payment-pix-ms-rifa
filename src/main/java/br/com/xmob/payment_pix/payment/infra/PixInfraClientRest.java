package br.com.xmob.payment_pix.payment.infra;

import br.com.xmob.payment_pix.payment.application.api.PaymentRequest;
import br.com.xmob.payment_pix.payment.application.service.PixClientRest;
import br.com.xmob.payment_pix.payment.domain.Payment;
import br.com.xmob.payment_pix.payment.domain.PixRequest;
import br.com.xmob.payment_pix.payment.domain.PixResponse;
import br.com.xmob.payment_pix.payment.domain.PixStatusResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class PixInfraClientRest implements PixClientRest {
    private final MercadoPagoFeignClient mercadoPagoFeignClient;
    private final PaymentRepository paymentRepository;


    @Override
    public PixResponse createCharge(PaymentRequest paymentRequest) {
        log.info("[start] PixInfraClientRest - createCharge");
        log.debug("[PaymentRequest] {}", paymentRequest);
        PixResponse pixResponse = mercadoPagoFeignClient.createCharge(new PixRequest(paymentRequest));
        log.debug("[PixResponse] {}", pixResponse);
        log.info("[finish] PixInfraClientRest - createCharge");
        return pixResponse;
    }

    @CircuitBreaker(name = "searchPixStatus", fallbackMethod = "searchPixStatusFallback")
    @Override
    public PixStatusResponse searchPixPaymentStatus(Payment payment) {
        log.info("[start] PixInfraClientRest - searchPixPaymentStatus");
        log.debug("[Payment] {}", payment);
        PixStatusResponse statusPix = mercadoPagoFeignClient.searchPixPaymentStatus(payment.getId());
        log.debug("[PixStatusResponse] {}", statusPix);
        log.info("[finish] PixInfraClientRest - searchPixPaymentStatus");
        return statusPix;
    }

    public PixStatusResponse searchPixStatusFallback(Payment payment, Exception ex) {
        log.info("[start] PixInfraClientRest - searchPixStatusFallback");
        log.error("[Error] ", ex);
        if (payment.isIntegrated()){
            payment.markAsNotIntegrated();
            paymentRepository.save(payment);
        }
        log.info("[finish] PixInfraClientRest - searchPixStatusFallback");
        throw new RuntimeException("Error ao buscar status pix", ex);
    }

}
