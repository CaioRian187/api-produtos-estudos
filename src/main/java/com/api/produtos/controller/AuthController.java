package com.api.produtos.controller;

import com.api.produtos.dtos.LoginRequestDTO;
import com.api.produtos.dtos.LoginResponseDTO;
import com.api.produtos.dtos.RegisterRequestDTO;
import com.api.produtos.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(this.authService.login(dto));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody RegisterRequestDTO dto){
        this.authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
