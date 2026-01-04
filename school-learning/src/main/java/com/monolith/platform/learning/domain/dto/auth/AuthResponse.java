package com.monolith.platform.learning.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@Builder
@JsonPropertyOrder({"username", "token", "message", "status"})
public record AuthResponse(
         String username,
         String token,
         String message,
         boolean status) {
}
