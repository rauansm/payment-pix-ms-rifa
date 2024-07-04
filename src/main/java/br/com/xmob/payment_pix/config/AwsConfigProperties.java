package br.com.xmob.payment_pix.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "aws.config")
@Component
public class AwsConfigProperties {
    private String region;
    private String accesskey;
    private String secretkey;
}

