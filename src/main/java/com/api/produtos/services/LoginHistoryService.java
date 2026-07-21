package com.api.produtos.services;

import com.api.produtos.config.security.CustomUserDetails;
import com.api.produtos.entities.LoginHistory;
import com.api.produtos.repositories.LoginHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;

    public void save(CustomUserDetails userDetails, String token){
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUserId(userDetails.getId());
        loginHistory.setEmail(userDetails.getEmail());
        loginHistory.setAccessToken(token);

        loginHistoryRepository.save(loginHistory);
    }
}
