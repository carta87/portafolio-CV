package com.monolith.platform.learning.config;

import com.monolith.platform.learning.persistence.repository.auth.IUsuarioRepository;
import com.monolith.platform.learning.persistence.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity
public class AplicationConfig {

    private final IUsuarioRepository iUsuarioRepository;
    private final UserDetailServiceImpl userDetailService;

    public AplicationConfig(IUsuarioRepository iUsuarioRepository, UserDetailServiceImpl userDetailService) {
        this.iUsuarioRepository = iUsuarioRepository;
        this.userDetailService = userDetailService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider()  {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return daoProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   /** @Bean
    public UserDetailsService userDetailService(){
        return username -> (UserDetails) iUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }*/
}
