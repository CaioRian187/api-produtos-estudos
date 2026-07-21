package com.api.produtos.dtos;

public record RegisterRequestDTO(
        String nome,
        String email,
        String senha
) {
}
