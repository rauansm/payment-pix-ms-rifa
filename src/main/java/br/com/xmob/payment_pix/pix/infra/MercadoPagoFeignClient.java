package br.com.xmob.payment_pix.pix.infra;

import br.com.xmob.payment_pix.pix.domain.PixExpiredRequest;
import br.com.xmob.payment_pix.pix.domain.PixRequest;
import br.com.xmob.payment_pix.pix.domain.PixResponse;
import br.com.xmob.payment_pix.pix.domain.PixStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "mercadoPagoClient", url = "${mercado-pago.pix.server}", configuration = MercadoPagoClientConfiguration.class)
public interface MercadoPagoFeignClient {
    @PostMapping("/v1/payments")
    PixResponse createCharge(@RequestBody PixRequest pixRequest);

    @GetMapping("/v1/payments/{id}")
    PixStatusResponse searchPixPaymentStatus(@PathVariable Long id);

    @PutMapping("/v1/payments/{paymentId}")
    void markPixPaymentAsExpired(@PathVariable Long paymentId,
                                 @RequestBody PixExpiredRequest pixExpiredRequest);
}
