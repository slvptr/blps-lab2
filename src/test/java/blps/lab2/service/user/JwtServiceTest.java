package blps.lab2.service.user;

import blps.lab2.model.domain.user.User;
import blps.lab2.model.domain.user.UserRole;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void generateAccessToken() {
        String email = "test@test.test";
        UserRole role = UserRole.USER;
        Long expiresIn = 123123L;

        User user = new User(email, "test", role, 100, new Date());
        String token = jwtService.generateAccessToken(user, expiresIn);
        Claims claims = jwtService.parseClaims(token);

        assertEquals(claims.get("email"), user.getEmail());
        assertEquals(claims.get("role"), user.getRole().name());
        assertEquals(claims.getSubject(), Long.toString(user.getId()));
    }

    @Test
    void isValid() {
        String email = "test@test.test";
        UserRole role = UserRole.USER;
        Long expiresIn = 123123L;

        User user = new User(email, "test", role, 100, new Date());
        String token = jwtService.generateAccessToken(user, expiresIn);


        assertTrue(jwtService.isValid(token));
    }
}