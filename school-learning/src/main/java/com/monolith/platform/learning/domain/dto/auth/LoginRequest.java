package com.monolith.platform.learning.domain.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @Email(message = "El email no tiene un formato válido")
        //@NotBlank
        String email) {
}
