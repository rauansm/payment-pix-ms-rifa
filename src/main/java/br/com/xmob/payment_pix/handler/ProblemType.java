package br.com.xmob.payment_pix.handler;

import lombok.Getter;

@Getter
public enum ProblemType {
    SYSTEM_ERROR("/erro-de-sistema", "Erro de sistema"),
    SERVICE_UNAVAILABLE("/servico-indisponivel", "Serviço indisponível"),
    INCOMPREHENSIBLE_MESSAGE("/mensagem-incompreensivel", "Mensagem incompreensível"),
    RESOURCE_NOT_FOUND("/recurso-nao-encontrado", "Recurso não encontrado"),
    BUSINESS_ERROR("/erro-negocio", "Violação de regra de negócio");

    private final String title;
    private final String uri;

    ProblemType(String path, String title) {
        this.uri = "https://xmob.com" + path;
        this.title = title;
    }

}
