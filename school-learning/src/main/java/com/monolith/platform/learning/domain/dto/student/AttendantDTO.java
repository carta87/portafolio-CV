package com.monolith.platform.learning.domain.dto.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public record AttendantDTO(
        Long id,

        @NotBlank(message = "El nombre del acudiente es obligatorio")
        String name,

        @NotBlank(message = "El apellido del acudiente es obligatorio")
        String lastName,

        @NotBlank(message = "El email del acudiente es obligatorio")
        @Email(message = "Formato de email inválido")
        String email) {
}