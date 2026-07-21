package com.api.produtos.services;

import com.api.produtos.config.security.CustomUserDetails;
import com.api.produtos.entities.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // Classe responsável por gerenciar a buscar o nosso UserDetails

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = this.usuarioService.findByEmail(email);

        if (usuario == null){
            throw new UsernameNotFoundException("User not found.");
        }

        return new CustomUserDetails(usuario);
    }
}
