package com.drow.user.application.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtHandler implements IJwtHandler {

    @Value("${JWT_SECRET}")
    private String jwtSecret;

    @Override
    public String generateToken(Map<String, Object> extraClaims, String correo) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(correo)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String getUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token) {
        return getClaimFromToken(token, Claims::getExpiration).after(new Date());
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String getCedulaFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("cedula", String.class));
    }


    @Override
    public Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key getSigningKey() {
        byte[] signingKey = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(signingKey);
    }
}
