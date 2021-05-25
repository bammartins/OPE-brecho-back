package br.com.gerenciamento.estoque.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;

import static java.util.Objects.nonNull;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expirantion;

    public String generationToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expirantion))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public boolean tokenValido(String token) {
        var claims = getClaims(token);
        if (nonNull(claims)) {
            String username = claims.getSubject();
            var expirantionDate = claims.getExpiration();
            var now = new Date(System.currentTimeMillis());

            if (nonNull(username) && nonNull(expirantionDate) && now.before(expirantionDate)) {
                return true;
            }
        }
        return false;
    }

    public String getUsername(String token) {
        var claims = getClaims(token);
        return !ObjectUtils.isEmpty(claims) ? claims.getSubject() : null;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

}
