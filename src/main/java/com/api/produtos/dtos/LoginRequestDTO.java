package com.api.produtos.dtos;

public record LoginRequestDTO(
        String email,
        String senha
) {
}
