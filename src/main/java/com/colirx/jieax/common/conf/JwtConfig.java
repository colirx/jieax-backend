package com.colirx.jieax.common.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private String secretKey;
    private String publicKey;
    private String privateKey;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;
}
