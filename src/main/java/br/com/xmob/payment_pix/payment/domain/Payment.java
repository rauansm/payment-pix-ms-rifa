package br.com.xmob.payment_pix.payment.domain;

import br.com.xmob.payment_pix.config.RabbitMQProperties;
import br.com.xmob.payment_pix.payment.application.api.PaymentRequest;
import br.com.xmob.payment_pix.pix.domain.PixResponse;
import br.com.xmob.payment_pix.pix.domain.PixStatusResponse;
import br.com.xmob.payment_pix.utils.PaymentStatus;
import br.com.xmob.payment_pix.utils.Date;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        this.orderId = paymentRequest.getOrderId();
        this.amount = pixResponse.getTransaction_amount();
        this.currencyId = pixResponse.getCurrency_id();
        this.paymentMethodId = pixResponse.getPayment_method_id();
        this.status = pixResponse.getStatus();
        this.statusDetail = pixResponse.getStatus_detail();
        this.integrated = true;
        this.createdAt = Date.adjustDate(pixResponse.getDate_created());
        this.updatedAt = Date.adjustDate(pixResponse.getDate_last_updated());
        this.expirationAt = this.createdAt.plusMinutes(paymentRequest.getPixExpirationTime());
        this.copiaCola = pixResponse.getPoint_of_interaction().getTransaction_data().getQr_code();
        this.qrCode = pixResponse.getPoint_of_interaction().getTransaction_data().getQr_code_base64();
    }

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
        return switch (payment.getStatus()) {
            case PaymentStatus.APPROVED -> rabbitmqProperties.getPaymentAprovedRoutingKey();
            case PaymentStatus.CANCELLED -> rabbitmqProperties.getPaymentExpiredRoutingKey();
            default -> throw new IllegalArgumentException("Status desconhecido: " + payment.status);
        };
    }

    public void markAsIntegrated() {
        this.integrated = true;
        this.updatedAt = LocalDateTime.now();
    }
}
