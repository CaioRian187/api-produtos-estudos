package com.api.produtos.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        BigDecimal preco,
        Boolean ativo,
        LocalDateTime dataCriacao,
        LocalDateTime dataUpdate
) {
}
