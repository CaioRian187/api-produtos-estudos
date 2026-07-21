package com.api.produtos.services;

import com.api.produtos.entities.Usuario;
import com.api.produtos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario create(Usuario usuario){
        return this.usuarioRepository.save(usuario);
    }

    public Usuario findByEmail(String email){
        return this.usuarioRepository.findByEmail(email);
    }
}
