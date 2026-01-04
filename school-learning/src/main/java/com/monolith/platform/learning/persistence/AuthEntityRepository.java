package com.monolith.platform.learning.persistence;

import com.monolith.platform.learning.domain.dto.auth.AuthResponse;
import com.monolith.platform.learning.domain.dto.auth.LoginRequest;
import com.monolith.platform.learning.domain.dto.auth.RegisterRequest;
import com.monolith.platform.learning.domain.repository.auth.IAuthRepository;
import com.monolith.platform.learning.persistence.entity.auth.PermissionEntity;
import com.monolith.platform.learning.persistence.entity.auth.RoleEntity;
import com.monolith.platform.learning.util.enums.RoleEnum;
import com.monolith.platform.learning.persistence.entity.auth.UserEntity;
import com.monolith.platform.learning.persistence.repository.auth.IPermisoRepository;
import com.monolith.platform.learning.persistence.repository.auth.IRolRepository;
import com.monolith.platform.learning.persistence.repository.auth.IUsuarioRepository;
import com.monolith.platform.learning.util.ConstantAuth;
import com.monolith.platform.learning.util.ConstantUser;
import com.monolith.platform.learning.util.enums.PermissionEnum;
import com.monolith.platform.learning.util.exception.ExceptionUtil;
import com.monolith.platform.learning.util.jwt.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;

@Repository
public class AuthEntityRepository implements IAuthRepository {

    private final JwtUtil jwtUtil;
    private final IUsuarioRepository iUsuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRolRepository iRolRepository;
    private final IPermisoRepository iPermisoRepository;
    private final AuthenticationManager authenticationManager;

    public AuthEntityRepository(JwtUtil jwtUtil, IUsuarioRepository iUsuarioRepository,
                                PasswordEncoder passwordEncoder, IRolRepository iRolRepository,
                                IPermisoRepository iPermisoRepository,
                                AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.iUsuarioRepository = iUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.iRolRepository = iRolRepository;
        this.iPermisoRepository = iPermisoRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        if (loginRequest.email() != null && iUsuarioRepository.findByEmail(loginRequest.email()).isEmpty()) {
            ExceptionUtil.getIllegalArgumentException(ConstantUser.ERROR_PASSWORD_NOT_FOUND);
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        String token = jwtUtil.getToken(iUsuarioRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new UsernameNotFoundException(ConstantUser.ERROR_NOT_FOUND_CREATED)));
        return AuthResponse.builder()
                .username(loginRequest.username())
                .status(Boolean.TRUE)
                .token(token)
                .message(ConstantAuth.TOKEN_CREATED)
                .build();
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {
        if (iUsuarioRepository.findByUsername(registerRequest.username()).isPresent()) {
            ExceptionUtil.getIllegalArgumentException(ConstantUser.ERROR_USER_CREATED);
        }

        if (iUsuarioRepository.findByEmail(registerRequest.email()).isPresent()) {
            ExceptionUtil.getIllegalArgumentException(ConstantUser.ERROR_PASSWORD_CREATED);
        }

        // Buscar o crear el permiso READ por defecto
        PermissionEntity readPermission = iPermisoRepository.findByName(PermissionEnum.READ.getValor())
                .orElseGet(() -> iPermisoRepository.save(PermissionEntity.builder()
                                .id(null)
                                .name(PermissionEnum.READ.getValor()).build()));

        // Buscar o crear el rol
        RoleEntity userRole = iRolRepository.findByRoleEnum(RoleEnum.USER)
                .orElseGet(() -> {
                    RoleEntity newRole = RoleEntity.builder()
                            .roleEnum(RoleEnum.USER)
                            .permissionList(new HashSet<>(Set.of(readPermission)))
                            .build();
                    return iRolRepository.save(newRole);
                });

        UserEntity userEntity = UserEntity.builder()
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .fistName(registerRequest.firstName())
                .lastName(registerRequest.lastName())
                .country(registerRequest.country())
                .email(registerRequest.email())
                .isEnable(Boolean.TRUE)
                .accountNoExpired(Boolean.TRUE)
                .credentialNoExpired(Boolean.TRUE)
                .accountNoLocked(Boolean.TRUE)
                .roles(Set.of(userRole))// Asociar el rol creado
                .build();
        iUsuarioRepository.save(userEntity);

        return AuthResponse.builder()
                .username(registerRequest.username())
                .token(jwtUtil.getToken(userEntity))
                .message(ConstantUser.USER_CREATED)
                .status(Boolean.TRUE)
                .build();
    }
}
