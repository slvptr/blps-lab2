package blps.lab2.service.user;

import blps.lab2.model.domain.user.User;
import blps.lab2.model.domain.user.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret}")
    private String secret;


    public String generateAccessToken(User user, Long expiresIn) {
        return generateAccessToken(user, expiresIn, new HashMap<>());
    }

    public String generateAccessToken(User user, Long expiresIn, Map<String, Object> extraClaims) {
        Map<String, Object> userClaims = new HashMap<>();
        userClaims.put("role", user.getRole().name());
        userClaims.put("email", user.getEmail());
        userClaims.put("remainingGrades", user.getRemainingGrades());
        userClaims.putAll(extraClaims);

        return Jwts
                .builder()
                .setClaims(userClaims)
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiresIn))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValid(String token) {
        try {
            if (isExpired(token)) {
                return false;
            }
            Claims claims = parseClaims(token);
            if (claims.getSubject() == null || claims.getSubject().length() == 0) {
                return false;
            }
            String role = claims.get("role").toString();
            boolean isRoleValid = false;
            for (UserRole elem : UserRole.values()) {
                if (elem.name().equals(role)) {
                    isRoleValid = true;
                    break;
                }
            }
            if (!isRoleValid) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    private boolean isExpired(String token) {
        Date expiresIn = parseClaims(token).getExpiration();
        return expiresIn.before(new Date());
    }


    private Key createSignInKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
