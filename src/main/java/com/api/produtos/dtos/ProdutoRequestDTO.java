package com.api.produtos.dtos;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        String nome,
        BigDecimal preco,
        Boolean ativo
) {
}
