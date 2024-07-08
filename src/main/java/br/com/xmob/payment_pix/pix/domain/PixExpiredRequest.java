package br.com.xmob.payment_pix.pix.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PixExpiredRequest {
    private String status;

    public PixExpiredRequest(String expired) {
        this.status = expired;
    }
}
