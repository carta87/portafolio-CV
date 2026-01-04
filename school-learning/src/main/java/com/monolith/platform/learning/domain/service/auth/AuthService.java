package com.monolith.platform.learning.domain.service.auth;

import com.monolith.platform.learning.domain.dto.auth.AuthResponse;
import com.monolith.platform.learning.domain.dto.auth.LoginRequest;
import com.monolith.platform.learning.domain.dto.auth.RegisterRequest;
import com.monolith.platform.learning.domain.repository.auth.IAuthRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final IAuthRepository iAuthRepository;

    public AuthService(IAuthRepository iAuthRepository) {
        this.iAuthRepository = iAuthRepository;
    }

    public AuthResponse login (LoginRequest loginRequest) {
        return this.iAuthRepository.login(loginRequest);
    }

    public AuthResponse register(RegisterRequest registerRequest){
        return this.iAuthRepository.register(registerRequest);
    }
}
