package com.fpt.a2thuantran.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * This class creates and reads JWT (JSON Web Token) used for login.
 * A JWT is a string that stores the account id and role, signed with a secret key
 * so that it cannot be modified by the client.
 */
@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expirationMilliseconds;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                    @Value("${jwt.expiration-ms}") long expirationMilliseconds) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMilliseconds = expirationMilliseconds;
    }

    // create a new token for the account that just logged in
    public String generateToken(Integer accountId, Integer accountRole) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationMilliseconds);

        return Jwts.builder()
                .subject(String.valueOf(accountId))
                .claim("role", accountRole)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    // read the account id that is stored inside the token
    public Integer getAccountIdFromToken(String token) {
        Claims claims = parseClaims(token);
        return Integer.valueOf(claims.getSubject());
    }

    // read the account role that is stored inside the token
    public Integer getAccountRoleFromToken(String token) {
        Claims claims = parseClaims(token);
        return claims.get("role", Integer.class);
    }

    // return true if the token is well-formed, signed correctly and not expired
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
