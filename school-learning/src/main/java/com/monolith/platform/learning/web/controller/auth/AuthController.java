package com.monolith.platform.learning.web.controller.auth;

import com.monolith.platform.learning.domain.dto.auth.AuthResponse;
import com.monolith.platform.learning.domain.dto.auth.LoginRequest;
import com.monolith.platform.learning.domain.dto.auth.RegisterRequest;
import com.monolith.platform.learning.domain.service.auth.AuthService;
import com.monolith.platform.learning.util.ConstantAuth;
import com.monolith.platform.learning.util.exception.ExceptionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Controller for Authentication")
public class AuthController {

    private final AuthService authService;
    private final CompromisedPasswordChecker passwordChecker;

    @Operation(
            summary = "Login User",
            description = "Authenticate a user and return the authentication token along with user details.",
            tags = {"Authentication"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "request login",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful authentication",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }


    @Operation(
            summary = "Register User",
            description = "Registro de usuario",
            tags = {"Authentication"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "request register",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful authentication",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/register")
    public  ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        CompromisedPasswordDecision decision =passwordChecker.check(registerRequest.password());
        if (decision.isCompromised()) {
            ExceptionUtil.getIllegalArgumentException(ConstantAuth.ERROR_PASSWORD_COMMON);
        }
        return ResponseEntity.ok(authService.register(registerRequest));
    }
}
