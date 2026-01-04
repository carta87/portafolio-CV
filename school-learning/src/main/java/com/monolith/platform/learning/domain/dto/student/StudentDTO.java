package com.monolith.platform.learning.domain.dto.student;

import com.monolith.platform.learning.domain.dto.course.CourseDTO;
import jakarta.validation.constraints.*;

public record StudentDTO(
        Long id,

        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "El apellido es obligatorio")
        String lastName,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        String email,

        CourseDTO courseDTO,

        AttendantDTO attendant) {
}
