package com.api.produtos.services;

import com.api.produtos.dtos.ProdutoRequestDTO;
import com.api.produtos.dtos.ProdutoResponseDTO;
import com.api.produtos.entities.Produto;
import com.api.produtos.repositories.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @InjectMocks
    ProdutoService produtoService;

    @Mock
    ProdutoRepository produtoRepository;

    Produto produto;
    ProdutoRequestDTO produtoRequestDTO;
    ProdutoResponseDTO produtoResponseDTO;

    @BeforeEach
    public void setUp(){
        produto = new Produto(
                12364L,
                "Arroz",
                BigDecimal.valueOf(5.00),
                true,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)
        );

        produtoRequestDTO = new ProdutoRequestDTO(
                "Arroz",
                BigDecimal.valueOf(5.00),
                true
        );

        produtoResponseDTO = new ProdutoResponseDTO(
                12364L,
                "Arroz",
                BigDecimal.valueOf(5.00),
                true,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)
        );
    }

    @Test
    void cadastrarProdutoComSucesso(){
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        var response = produtoService.create(produtoRequestDTO);

        assertEquals(response, produtoResponseDTO);
        assertNotNull(response.dataCriacao());
        assertNotNull(response.dataUpdate());
        verify(produtoRepository).save(any(Produto.class));
        verifyNoMoreInteractions(produtoRepository);
    }

    @Test
    void naoCadastrarProdutoPassandoObjetoVazio(){

        ProdutoRequestDTO dtoNulo = new ProdutoRequestDTO(null,null,null);
        Produto produtoNulo = new Produto(null, null, null, null, null, null);

        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoNulo);

        var response = produtoService.create(dtoNulo);

        assertNull(response.id());
        assertNull(response.nome());
        assertNull(response.ativo());
        assertNull(response.dataCriacao());
        assertNull(response.dataUpdate());

        verify(produtoRepository).save(any(Produto.class));
        verifyNoMoreInteractions(produtoRepository);
    }

    @Test
    void buscarTodosOsProdutosComSucesso(){

        Pageable pageable = PageRequest.of(0, 10);

        Page<Produto> produtoPage = new PageImpl<>(List.of(produto));

        when(produtoRepository.findAll(pageable)).thenReturn(produtoPage);

        var response = produtoService.findAll(pageable.getPageNumber(), pageable.getPageSize());

        assertNotNull(response);
        assertEquals(1, response.getSize());
        verify(produtoRepository).findAll(pageable);
        verifyNoMoreInteractions(produtoRepository);
    }

    @Test
    void buscarTodosOsProdutosSemSucesso(){
        Pageable pageable = PageRequest.of(0, 10);

        Page<Produto> produtoPageVazio = new PageImpl<>(List.of());

        when(produtoRepository.findAll(pageable)).thenReturn(produtoPageVazio);

        var response = produtoService.findAll(pageable.getPageNumber(), pageable.getPageSize());

        assertNotNull(response);
        assertEquals(0, response.getTotalElements());
        verify(produtoRepository).findAll(pageable);
        verifyNoMoreInteractions(produtoRepository);
    }
}
