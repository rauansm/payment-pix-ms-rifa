package br.com.xmob.payment_pix.payment.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PixStatusResponse {
    private String status;
    private String status_detail;
    private String date_created;
    private String date_last_updated;
    private String date_of_expiration;
}
