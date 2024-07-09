package br.com.xmob.payment_pix.strategy;

public interface RoutingKeyStrategy {
    String determineRoutingKeyByStatus();
    String getPaymentStatus();
}
