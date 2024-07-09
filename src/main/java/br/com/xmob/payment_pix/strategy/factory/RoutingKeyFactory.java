package br.com.xmob.payment_pix.strategy.factory;

import br.com.xmob.payment_pix.handler.APIException;
import br.com.xmob.payment_pix.strategy.RoutingKeyStrategy;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Log4j2
public class RoutingKeyFactory {

    private final Map<String, RoutingKeyStrategy> strategies = new HashMap<>();

    public RoutingKeyFactory(Set<RoutingKeyStrategy> strategySet){
        strategySet.forEach(s -> strategies.put(s.getPaymentStatus(), s));

    }

    public RoutingKeyStrategy getStrategy(String paymentStatus) {
        log.info("[start] RoutingKeyFactory - getStrategy");
        log.debug("[PaymentStatus] {}", paymentStatus);
        RoutingKeyStrategy strategy = strategies.get(paymentStatus);
        if(strategy == null){
            throw APIException.business("Status desconhecido: " + paymentStatus);
        }
        log.info("[finish] RoutingKeyFactory - getStrategy");
        return strategy;

    }
}
