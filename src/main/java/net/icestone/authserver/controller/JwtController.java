package net.icestone.authserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.icestone.authserver.contoller.model.CreateJwtApiRequest;
import net.icestone.authserver.contoller.model.CreateJwtApiResponse;
import net.icestone.authserver.jwt.JwtService;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/jwt")
    public ResponseEntity getJwt(@RequestBody CreateJwtApiRequest request) {
        if("The detonator".equals(request.getUsername())) {
            return ResponseEntity.ok(
                CreateJwtApiResponse.builder()
                    .token(jwtService.generateToken(request.getUsername()))
                    .build()
            );
        } else {
            return ResponseEntity.ok("Sorry you are not 'The detonator'");
        }

    }
}