package com.api.produtos.controller;

import com.api.produtos.dtos.ProdutoRequestDTO;
import com.api.produtos.dtos.ProdutoResponseDTO;
import com.api.produtos.entities.Produto;
import com.api.produtos.services.ProdutoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> create(@RequestBody ProdutoRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.produtoService.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<Produto>> findAll(@RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.status(HttpStatus.OK).body(this.produtoService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.produtoService.findById(id));
    }
}
