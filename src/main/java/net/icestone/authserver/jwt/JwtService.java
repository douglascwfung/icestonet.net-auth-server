package net.icestone.authserver.jwt;



import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import net.icestone.authserver.config.JwtConfig;
import net.icestone.authserver.util.DateUtil;


@Component
@RequiredArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;
    private final JwtKeyProvider jwtKeyProvider;
    private final DateUtil dateUtil;

    public String generateToken(String username) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryDate = now.plusMinutes(jwtConfig.getExpirationInMinutes());

        return Jwts.builder()
            .setExpiration(dateUtil.toDate(expiryDate))
            .signWith(SignatureAlgorithm.RS256, jwtKeyProvider.getPrivateKey())
            .claim("username", username)
            .compact();
    }
}
