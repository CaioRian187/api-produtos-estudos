package com.api.produtos.dtos;

public record LoginResponseDTO(
        String accessToken,
        String refreshToken
) {
}
