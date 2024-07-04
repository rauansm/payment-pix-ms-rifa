package br.com.xmob.payment_pix.payment.infra;

import br.com.xmob.payment_pix.payment.application.api.PaymentEvent;
import br.com.xmob.payment_pix.payment.application.api.PaymentRequest;
import br.com.xmob.payment_pix.payment.application.service.PixClientRest;
import br.com.xmob.payment_pix.payment.domain.PixRequest;
import br.com.xmob.payment_pix.payment.domain.PixResponse;
import br.com.xmob.payment_pix.payment.domain.PixStatusResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class PixInfraClientRest implements PixClientRest {
    private final MercadoPagoFeignClient mercadoPagoFeignClient;

    @Override
    public PixResponse createCharge(PaymentRequest paymentRequest) {
        log.info("[start] PixInfraClientRest - createCharge");
        log.debug("[PaymentRequest] {}", paymentRequest);
        PixResponse pixResponse = mercadoPagoFeignClient.createCharge(new PixRequest(paymentRequest));
        log.debug("[PixResponse] {}", pixResponse);
        log.info("[finish] PixInfraClientRest - createCharge");
        return pixResponse;
    }

    @Override
    public PixStatusResponse searchPixPaymentStatus(PaymentEvent paymentEvent) {
        log.info("[start] PixInfraClientRest - searchPixPaymentStatus");
        log.debug("[PaymentEvent] {}", paymentEvent);
        PixStatusResponse statusPix = mercadoPagoFeignClient.searchPixPaymentStatus(paymentEvent.getData().getId());
        log.debug("[PixStatusResponse] {}", statusPix);
        log.info("[finish] PixInfraClientRest - searchPixPaymentStatus");
        return statusPix;
    }
}
