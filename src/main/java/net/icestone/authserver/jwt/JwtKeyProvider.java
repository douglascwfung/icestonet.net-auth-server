package net.icestone.authserver.jwt;

import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.icestone.authserver.contoller.jwt.model.exception.JwtInitializationException;
import net.icestone.authserver.util.Base64Util;
import net.icestone.authserver.util.ResourceUtil;



@Component
@RequiredArgsConstructor
public class JwtKeyProvider {

    private final ResourceUtil resourceUtil;
    private final Base64Util base64Util;

    @Getter
    private PrivateKey privateKey;

    @PostConstruct
    public void init() {
        privateKey = readKey(
//            "classpath:keys/rsa-key.pkcs8.private",
        	"classpath:keys/pkcs8.key",
            "PRIVATE",
            this::privateKeySpec,
            this::privateKeyGenerator
        );
    }

    private <T extends Key> T readKey(String resourcePath, String headerSpec, Function<String, EncodedKeySpec> keySpec, BiFunction<KeyFactory, EncodedKeySpec, T> keyGenerator) {
        try {
            String keyString = resourceUtil.asString(resourcePath);
            //TODO you can check the headers and throw an exception here if you want

            keyString = keyString
                .replace("-----BEGIN " + headerSpec + " KEY-----", "")
                .replace("-----END " + headerSpec + " KEY-----", "")
                .replaceAll("\\s+", "");


            
            System.out.println("keyString:" + keyString);
            
            return keyGenerator.apply(KeyFactory.getInstance("RSA"), keySpec.apply(keyString));
        } catch(NoSuchAlgorithmException | IOException e) {
            throw new JwtInitializationException(e);
        }
    }

    private EncodedKeySpec privateKeySpec(String data) {
    	System.out.println("data:" + data);
        return new PKCS8EncodedKeySpec(base64Util.decode(data));
    }

    private PrivateKey privateKeyGenerator(KeyFactory kf, EncodedKeySpec spec) {
        try {
            return kf.generatePrivate(spec);
        } catch(InvalidKeySpecException e) {
            throw new JwtInitializationException(e);
        }
    }

}

