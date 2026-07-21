package com.api.produtos.services;


import com.api.produtos.config.security.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JwtService {

    // Classe responsável por criar os tokens

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-minutes}")
    private long expirationMinutes;


    public String generateToken(CustomUserDetails userDetails){
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", userDetails.getId());
        claims.put("nome", userDetails.getNome());
        claims.put("email", userDetails.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(
                        Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

}
