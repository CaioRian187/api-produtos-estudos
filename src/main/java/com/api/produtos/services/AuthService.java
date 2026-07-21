package com.api.produtos.services;

import com.api.produtos.config.security.AuthenticationConfig;
import com.api.produtos.config.security.CustomUserDetails;
import com.api.produtos.config.security.PasswordEnconderConfig;
import com.api.produtos.dtos.LoginRequestDTO;
import com.api.produtos.dtos.LoginResponseDTO;
import com.api.produtos.dtos.RegisterRequestDTO;
import com.api.produtos.entities.RefreshToken;
import com.api.produtos.entities.Usuario;
import com.api.produtos.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final LoginHistoryService loginHistoryService;
    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO dto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.senha()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(userDetails);

        RefreshToken refreshToken = refreshTokenService.create(userDetails.getId());

        loginHistoryService.save(userDetails, accessToken);

        return new LoginResponseDTO(accessToken,refreshToken.getToken());
    }

    public void register(RegisterRequestDTO dto){
        if (usuarioRepository.findByEmail(dto.email()) != null){
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setAtivo(Boolean.TRUE);
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        this.usuarioRepository.save(usuario);
    }
}
