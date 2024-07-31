package br.com.xmob.payment_pix.payment.application.service;

import br.com.xmob.payment_pix.payment.domain.Payment;
import br.com.xmob.payment_pix.payment.infra.PaymentRepository;
import br.com.xmob.payment_pix.pix.application.service.PixService;
import br.com.xmob.payment_pix.utils.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static br.com.xmob.payment_pix.utils.PaymentStatus.PENDING;

@Service
@Log4j2
@RequiredArgsConstructor
public class PaymentExpirationService {

    private final PaymentRepository paymentRepository;
    private final PixService pixService;

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void checkForExpiredPayments() {
        log.debug("[start] PaymentExpirationService - checkForExpiredPayments");
        List<Payment> pendingPayments = paymentRepository.searchPendingPaymentsWithIntegration(PENDING);
        log.debug("[PendingPayments] {}", pendingPayments);
        pendingPayments.forEach(payment -> {
            if (LocalDateTime.now().isAfter(payment.getExpirationAt())) {
                markPaymentAsExpired(payment);
            }
        });
        log.debug("[finish] PaymentExpirationService - checkForExpiredPayments");
    }

    private void markPaymentAsExpired(Payment payment) {
        log.debug("[start] PaymentExpirationService - markPaymentAsExpired");
        pixService.markPixPaymentAsExpired(payment.getId(), PaymentStatus.CANCELLED);
        paymentRepository.save(payment);
        log.debug("[finish] PaymentExpirationService - markPaymentAsExpired");
    }
}
