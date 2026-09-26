package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRATION;

    @Value("${jwt.refresh-expiration}")
    private long REFRESH_EXPIRATION;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(
                        StandardCharsets.UTF_8
                )
        );
    }

    public String generateToken(
            String email
    ) {
        Date now = new Date();

        Date expiration =
                new Date(
                        now.getTime()
                                + EXPIRATION
                );

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(
            String email
    ) {
        Date now = new Date();

        Date expiration =
                new Date(
                        now.getTime()
                                + REFRESH_EXPIRATION
                );

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public <T> T getClaimFromToken(
            String token,
            Function<Claims, T> claimsResolver
    ) {
        Claims claims =
                getAllClaimsFromToken(token);

        return claimsResolver.apply(
                claims
        );
    }

    private Claims getAllClaimsFromToken(
            String token
    ) {
        return Jwts.parser()
                .verifyWith(
                        getSigningKey()
                )
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsernameFromToken(
            String token
    ) {
        return getClaimFromToken(
                token,
                Claims::getSubject
        );
    }

    public boolean validateToken(
            String token
    ) {
        try {
            Jwts.parser()
                    .verifyWith(
                            getSigningKey()
                    )
                    .build()
                    .parseSignedClaims(
                            token
                    );

            return true;

        } catch (
                JwtException |
                IllegalArgumentException exception
        ) {
            return false;
        }
    }
}