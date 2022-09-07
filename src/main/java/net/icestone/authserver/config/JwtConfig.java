package net.icestone.authserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private Long expirationInMinutes;
}
