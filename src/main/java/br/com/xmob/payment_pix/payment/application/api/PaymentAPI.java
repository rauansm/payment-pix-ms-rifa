package br.com.xmob.payment_pix.payment.application.api;

import br.com.xmob.payment_pix.payment.application.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/payment")
@Log4j2
@RequiredArgsConstructor
public class PaymentAPI {

    private final PaymentService paymentService;

    @PostMapping(value = "/cob")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PaymentResponse createCharge(@RequestBody @Valid PaymentRequest paymentRequest){
        log.info("[start] PaymentAPI - createCharge");
        log.debug("[PaymentRequest] {}", paymentRequest);
        PaymentResponse paymentResponse = paymentService.createCharge(paymentRequest);
        log.info("[finish] PaymentAPI - createCharge");
        return paymentResponse;
    }
}
