package com.api.produtos.services;

import com.api.produtos.dtos.ProdutoRequestDTO;
import com.api.produtos.dtos.ProdutoResponseDTO;
import com.api.produtos.entities.Produto;
import com.api.produtos.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoResponseDTO create(ProdutoRequestDTO dto){
        Produto produto = new Produto(dto.nome(), dto.preco(), dto.ativo());

        this.produtoRepository.save(produto);

        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getAtivo(),
                produto.getDataCriacao(),
                produto.getDataUpdate()
        );
    }
}
