package com.drow.user.application.jwt;

import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Map;

public interface IJwtHandler {
    String generateToken(Map<String, Object> extraClaims);
    String getUsername(String token);
    boolean isTokenValid(String token);
    Claims getClaims(String token);
    Key getSigningKey();
}
