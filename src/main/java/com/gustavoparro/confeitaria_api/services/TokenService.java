package com.gustavoparro.confeitaria_api.services;

import com.gustavoparro.confeitaria_api.models.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${confeitaria.api.jwt.secret}")
    private String secret;

    @Value("${confeitaria.api.jwt.expiration}")
    private String expiration;

    public String generateToken(Authentication authentication) {
        AppUser user = (AppUser) authentication.getPrincipal();

        return Jwts.builder()
                .setIssuer("confeitaria-api")
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + Long.parseLong(expiration)))
                .signWith(SignatureAlgorithm.HS256 , secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            return true;
        } catch (Exception exception) {
            exception.printStackTrace();

            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

}
