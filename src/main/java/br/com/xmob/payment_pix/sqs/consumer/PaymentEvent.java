package br.com.xmob.payment_pix.sqs.consumer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PaymentEvent {
    @JsonProperty("action")
    private String action;
    @JsonProperty("api_version")
    private String api_version;
    @JsonProperty("data")
    private Data data;
    @JsonProperty("date_created")
    private String date_created;
    @JsonProperty("id")
    private String id;
    @JsonProperty("live_mode")
    private String live_mode;
    @JsonProperty("type")
    private String type;
    @JsonProperty("user_id")
    private String user_id;

    @Getter
    @ToString
    public static class Data {
        @JsonProperty("id")
        private String id;
    }
}
