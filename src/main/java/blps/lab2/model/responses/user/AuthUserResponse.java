package blps.lab2.model.responses.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthUserResponse {
    private String accessToken;
    private Long expiresIn;
}
