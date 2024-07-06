package br.com.xmob.payment_pix.payment.domain;

import br.com.xmob.payment_pix.config.RabbitMQProperties;
import br.com.xmob.payment_pix.payment.application.api.PaymentRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@Document(collection = "payment")
public class Payment {
    private Long id;
    private String payerName;
    private String payerCPF;
    private UUID orderId;
    private BigDecimal amount;
    private String currencyId;
    private String paymentMethodId;
    private String status;
    private String statusDetail;
    private boolean integrated;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime expirationAt;
    private String copiaCola;
    private String qrCode;

    public Payment(PixResponse pixResponse, PaymentRequest paymentRequest) {
        this.id = pixResponse.getId();
        this.payerName = paymentRequest.getName();
        this.payerCPF = paymentRequest.getCpf();
        this.orderId = UUID.fromString(pixResponse.getExternal_reference());
        this.amount = pixResponse.getTransaction_amount();
        this.currencyId = pixResponse.getCurrency_id();
        this.paymentMethodId = pixResponse.getPayment_method_id();
        this.status = pixResponse.getStatus();
        this.statusDetail = pixResponse.getStatus_detail();
        this.integrated = true;
        this.createdAt = adjustDate(pixResponse.getDate_created());
        this.updatedAt = adjustDate(pixResponse.getDate_last_updated());
        this.expirationAt = adjustDate(pixResponse.getDate_of_expiration());
        this.copiaCola = pixResponse.getPoint_of_interaction().getTransaction_data().getQr_code();
        this.qrCode = pixResponse.getPoint_of_interaction().getTransaction_data().getQr_code_base64();
    }

    protected LocalDateTime adjustDate(String date){
        OffsetDateTime adjustedDateTime;
        try {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(date, FORMATTER);
        adjustedDateTime = offsetDateTime.withOffsetSameInstant(ZoneOffset.of("-03:00"));
        } catch (Exception e){
            return null;
        }
        return adjustedDateTime.toLocalDateTime();
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public void markAsNotIntegrated() {
        this.integrated = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateStatus(PixStatusResponse statusPayment) {
        this.status = statusPayment.getStatus();
        this.statusDetail = statusPayment.getStatus_detail();
        this.updatedAt = LocalDateTime.now();
    }

    public String determineRoutingKeyByStatus(Payment payment, RabbitMQProperties rabbitmqProperties) {
        if (payment.status.equals("approved")) {
            return rabbitmqProperties.getPaymentAprovedRoutingKey();
        }
        return rabbitmqProperties.getPaymentExpiredRoutingKey();
    }
}
