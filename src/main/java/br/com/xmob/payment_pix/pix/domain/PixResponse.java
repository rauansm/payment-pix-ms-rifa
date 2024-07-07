package br.com.xmob.payment_pix.pix.domain;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class PixResponse {
    private Long id;
    private String external_reference;
    private BigDecimal transaction_amount;
    private String currency_id;
    private String payment_method_id;
    private String status;
    private String status_detail;
    private String date_created;
    private String date_last_updated;
    private String date_of_expiration;
    private PointOfInteraction point_of_interaction;


    @Getter
    @ToString
    public static class PointOfInteraction {
        private TransactionData transaction_data;

    }

    @Getter
    @ToString
    public static class TransactionData {
        private String qr_code;
        private String qr_code_base64;

    }
}
