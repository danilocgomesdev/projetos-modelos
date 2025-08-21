package fieg.externos.cielocheckout.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class CieloEcommerceToken {

    @JsonProperty("access_token")
    private String token;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int expiresIn;

    // Não faz parte da resposta da Cielo
    @JsonIgnore
    private Instant expiresAt;

    // Usado pelo jackson
    @SuppressWarnings("unused")
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
        this.expiresAt = Instant.now().plusSeconds(expiresIn);
    }
}
