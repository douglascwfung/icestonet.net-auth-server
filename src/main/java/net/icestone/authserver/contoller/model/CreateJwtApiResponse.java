package net.icestone.authserver.contoller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateJwtApiResponse {
    @Builder.Default
    private String tokenType = "Bearer";
    private String token;
}
