package br.com.xmob.payment_pix.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter(value = AccessLevel.PACKAGE)
@ToString
@Component
@ConfigurationProperties(prefix = "aws.config")
public class AwsConfigProperties {
    private String region;
    private String accesskey;
    private String secretkey;
}

