package com.api.produtos.services;

import com.api.produtos.entities.RefreshToken;
import com.api.produtos.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${security.jwt.refresh-expiration-minutes}")
    private long refreshExpiration;

    public RefreshToken create(Long userId){
        RefreshToken token = new RefreshToken();
        token.setUserId(userId);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusMinutes(refreshExpiration));
        token.setRevoked(false);
        return refreshTokenRepository.save(token);
    }
}
