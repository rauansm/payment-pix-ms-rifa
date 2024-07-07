package br.com.xmob.payment_pix.pix.domain;

import br.com.xmob.payment_pix.payment.application.api.PaymentRequest;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
public class PixRequest {
        private String date_of_expiration;
        private String external_reference;
        private String payment_method_id;
        private BigDecimal transaction_amount;
        private Payer payer;



    public PixRequest(PaymentRequest paymentRequest) {
        this.date_of_expiration = calculateExpirationDateTime();
        this.external_reference = String.valueOf(paymentRequest.getOrderId());
        this.payment_method_id = METHOD_PAYMENT;
        this.transaction_amount = paymentRequest.getAmount();
        this.payer = new Payer(paymentRequest);
    }

    private String calculateExpirationDateTime() {
        return OffsetDateTime.now(SAO_PAULO_ZONE)
                .plusMinutes(EXPIRATION_MINUTES)
                .format(DATE_TIME_FORMATTER);
    }


    @Getter
    @ToString
    public class Payer {
        private String first_name;
        private String email;
        private Identification identification;

        public Payer(PaymentRequest paymentRequest) {
            this.first_name = paymentRequest.getName();
            this.email = EMAIL;
            this.identification = new Identification(paymentRequest.getCpf());
        }
    }


    @Getter
    @ToString
    public class Identification {
        private String type;
        private String number;

        public Identification(String cpf) {
            this.type = TYPE_DOCUMENT;
            this.number = cpf;
        }
    }

    private static final ZoneId SAO_PAULO_ZONE = ZoneId.of("America/Sao_Paulo");
    private static final String METHOD_PAYMENT = "pix";
    private static final String EMAIL = "rauanmoreia01@hotmail.com";
    private static final String TYPE_DOCUMENT = "CPF";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    private static final Integer EXPIRATION_MINUTES = 10;


}
