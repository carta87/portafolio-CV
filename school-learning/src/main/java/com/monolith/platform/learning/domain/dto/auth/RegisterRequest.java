package com.monolith.platform.learning.domain.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        @Email(message = "El email no tiene un formato válido")
        String email,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String country) {
}
