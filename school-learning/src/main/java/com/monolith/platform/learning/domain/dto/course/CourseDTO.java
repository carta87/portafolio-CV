package com.monolith.platform.learning.domain.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CourseDTO (
        Long id,

        @NotNull(message = "El número del curso es obligatorio")
        @Positive(message = "El número del curso debe ser mayor que cero")
        Long numberCourse,

        @NotBlank(message = "El nombre del curso es obligatorio")
        String name,

        String teacher){
}