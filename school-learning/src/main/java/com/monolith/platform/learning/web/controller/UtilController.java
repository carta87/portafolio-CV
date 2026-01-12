package com.monolith.platform.learning.web.controller;

import com.monolith.platform.learning.domain.service.GeneralAi.AiServiceGeneral;
import com.monolith.platform.learning.util.ConstantGeneral;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/util")
@RequiredArgsConstructor
public class UtilController {

    @Value("${spring.application.name}")
    private String platform;
    private final AiServiceGeneral aiServiceGeneral;

    //@PreAuthorize("denyAll()")
    //@PreAuthorize("hasAuthority('READ')")
    //@PreAuthorize("hasRole('USER')")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/text")
    public String login() {
        return "entraste en protegido";
    }

    @GetMapping("/welcomeCoursePlatform")
    public ResponseEntity<String> welcomeCoursePlatform(){
        try {
            return ResponseEntity.ok(aiServiceGeneral.welcomeCoursePlatform(
                    platform,
                    ConstantGeneral.FALLBACK_MESSAGE
            ));
        } catch (Exception ex) {
            // Log del error (saldo, timeout, red, etc.)
            log.warn("IA no disponible, usando mensaje por defecto", ex);
            return ResponseEntity.ok(ConstantGeneral.FALLBACK_MESSAGE);
        }
    }

}
