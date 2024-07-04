package br.com.xmob.payment_pix.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class ListenerSQSConfig {
    private final AwsConfigProperties awsSqsProperties;

    @Primary
    @Bean
    public AmazonSQSAsync amazonSQSAsync() {
        log.debug("[start] ListenerSQSConfig - amazonSQSAsync");
        log.debug("[awsSnsProperties] {}", awsSqsProperties);
        AmazonSQSAsyncClientBuilder clientBuilder = AmazonSQSAsyncClientBuilder.standard()
                .withRegion(awsSqsProperties.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsSqsProperties.getAccesskey(), awsSqsProperties.getSecretkey())));
        AmazonSQSAsync clientSQS = clientBuilder.build();
        log.debug("[finish] ListenerSQSConfig - amazonSQSAsync");
        return clientSQS;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new MappingJackson2MessageConverter();
    }
}
