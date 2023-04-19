package blps.lab2.model.requests.user;

import lombok.Data;

@Data
public class AuthUserRequest {
    private String email;
    private String password;
}
