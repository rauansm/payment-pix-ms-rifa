package br.com.xmob.payment_pix.pix.infra;

import feign.RequestInterceptor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@Log4j2
public class MercadoPagoClientConfiguration {
    private String accessToken;

    @Bean
    public RequestInterceptor feignRequestInterceptor() {
        log.debug("[start] MercadoPagoClientConfiguration - feignRequestInterceptor");
        return template -> {
            template.header("Authorization", accessToken);
        template.header("X-Idempotency-Key", UUID.randomUUID().toString());
        log.debug("[finish] MercadoPagoClientConfiguration - feignRequestInterceptor");
        };
    }

    @Autowired
    public void setAccessToken(@Value("${mercado-pago.pix.accessToken}") String accessToken) {
        this.accessToken = accessToken;
    }
}
