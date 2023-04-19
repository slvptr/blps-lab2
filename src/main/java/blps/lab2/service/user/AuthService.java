package blps.lab2.service.user;

import blps.lab2.controller.exceptions.AuthenticationFailException;
import blps.lab2.controller.exceptions.InternalServerException;
import blps.lab2.controller.exceptions.InvalidDataException;
import blps.lab2.model.domain.user.User;
import blps.lab2.model.domain.user.UserRole;
import blps.lab2.model.requests.user.AuthUserRequest;
import blps.lab2.model.responses.user.AuthUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

@Service
public class AuthService {
    private JwtService jwtService;
    private UserService userService;
    private PasswordService passwordService;

    @Value("${application.security.jwt.expiresIn}")
    private Long tokenExpiresIn;

    @Autowired
    public AuthService(JwtService jwtService, UserService userService, PasswordService passwordService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordService = passwordService;
    }

    public User register(AuthUserRequest req) {
        try {
            String password = req.getPassword().trim();
            String hashedPassword = passwordService.hashCode(password);
            User user = new User(
                    req.getEmail().trim().toLowerCase(),
                    hashedPassword,
                    UserRole.USER,
                    100,
                    new Date()
            );
            if (password.length() <= 3) throw new InvalidDataException("Password length must be at least 3");

            return userService.createUser(user);

        } catch (NoSuchAlgorithmException e) {
            throw new InternalServerException(e.getMessage());
        }
    }

    public AuthUserResponse login(AuthUserRequest req) {
        try {
            User user = userService.getUserByEmail(req.getEmail())
                    .orElseThrow(() -> new AuthenticationFailException("Such user doesn't exist"));

            if (!user.getPassword().equals(passwordService.hashCode(req.getPassword()))) {
                throw new AuthenticationFailException("Invalid password");
            }

            String token = jwtService.generateAccessToken(user, tokenExpiresIn);
            return new AuthUserResponse(token, tokenExpiresIn);

        } catch (NoSuchAlgorithmException e) {
            throw new InternalServerException();
        }
    }
}
