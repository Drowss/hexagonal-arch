package com.drow.user.application.jwt;

import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Map;

public interface IJwtHandler {
    String generateToken(Map<String, Object> extraClaims, String correo);
    String getUsername(String token);
    boolean isTokenValid(String token);
    Claims getClaims(String token);
    Key getSigningKey();
    String getCedulaFromToken(String token);
}
