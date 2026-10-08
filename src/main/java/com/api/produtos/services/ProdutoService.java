package com.api.produtos.services;

import com.api.produtos.dtos.ProdutoRequestDTO;
import com.api.produtos.dtos.ProdutoResponseDTO;
import com.api.produtos.entities.Produto;
import com.api.produtos.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoResponseDTO create(ProdutoRequestDTO dto){
        Produto produto = new Produto(dto.nome(), dto.preco(), dto.ativo());

        Produto produtoSalvo = this.produtoRepository.save(produto);

        return new ProdutoResponseDTO(
                produtoSalvo.getId(),
                produtoSalvo.getNome(),
                produtoSalvo.getPreco(),
                produtoSalvo.getAtivo(),
                produtoSalvo.getDataCriacao(),
                produtoSalvo.getDataUpdate()
        );
    }

    public Page<Produto> findAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return this.produtoRepository.findAll(pageable);
    }

    public ProdutoResponseDTO findById(Long id){
        return this.produtoRepository.findDtoById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Produto não encontrado."
                ));
    }
}
