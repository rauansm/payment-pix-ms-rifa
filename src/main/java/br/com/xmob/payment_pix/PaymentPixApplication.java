package br.com.xmob.payment_pix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PaymentPixApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentPixApplication.class, args);
	}

}
