package br.com.xmob.payment_pix.payment.application.api;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@ToString
public class PaymentRequest {
    @NotBlank(message = "O campo nome é obrigatório e não pode estar em branco.")
    private String name;
    @CPF
    @NotBlank(message = "O campo cpf é obrigatório e não pode estar em branco.")
    private String cpf;
    @NotNull(message = "O campo amount é obrigatório e não pode ser nulo.")
    private BigDecimal amount;
    @NotNull(message = "O campo OrderId é obrigatório e não pode ser nulo.")
    private UUID orderId;
    @NotNull(message = "O campo pixExpirationTime é obrigatório e não pode ser nulo.")
    private Integer pixExpirationTime;

}
