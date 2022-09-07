package net.icestone.authserver.contoller.jwt.model.exception;

public class JwtInitializationException extends RuntimeException {
    public JwtInitializationException(Throwable e) {
        super("Something went wong while reading private key!", e);
        System.out.println(e);
    }
}
