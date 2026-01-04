package com.monolith.platform.learning.domain.dto.util;

import lombok.Builder;

@Builder
public record ErrorDTO (
        String type,
        String message) {
}