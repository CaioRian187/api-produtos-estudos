package com.api.produtos.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "login_history")
@Table(name = "login_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String email;

    private String accessToken;


    // Outra opção caso não queira utilizar a anotação @CreationTimestamp
    private LocalDateTime loginAt;

    @PrePersist
    void onLogin(){
        this.loginAt = LocalDateTime.now();
    }

}
