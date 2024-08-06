package com.drow.plazoleta.domain.spi;

import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Map;
import java.util.function.Function;

public interface IJwtHandler {
    String generateToken(Map<String, Object> extraClaims, String correo);

    String getUsername(String token);

    boolean isTokenValid(String token);

    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);

    Integer getCedulaFromToken(String token);

    Claims getClaims(String token);

    Key getSigningKey();
}
