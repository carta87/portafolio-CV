package com.monolith.platform.learning.domain.repository.auth;

import com.monolith.platform.learning.domain.dto.auth.AuthResponse;
import com.monolith.platform.learning.domain.dto.auth.LoginRequest;
import com.monolith.platform.learning.domain.dto.auth.RegisterRequest;

public interface IAuthRepository {
    AuthResponse login (LoginRequest loginRequest) ;

    AuthResponse register(RegisterRequest registerRequest);
}
