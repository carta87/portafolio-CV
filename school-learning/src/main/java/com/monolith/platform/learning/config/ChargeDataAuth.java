package com.monolith.platform.learning.config;

import com.monolith.platform.learning.persistence.entity.auth.PermissionEntity;
import com.monolith.platform.learning.persistence.entity.auth.RoleEntity;
import com.monolith.platform.learning.util.enums.PermissionEnum;
import com.monolith.platform.learning.util.enums.RoleEnum;
import com.monolith.platform.learning.persistence.entity.auth.UserEntity;
import com.monolith.platform.learning.persistence.repository.auth.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;

@Component
@Order(1)
@RequiredArgsConstructor
public class ChargeDataAuth implements CommandLineRunner {

    private final IUsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        List<UserEntity> entityList= (List<UserEntity>) userRepository.findAll();
        if(entityList.isEmpty()){
            /* Create PERMISSIONS */
            PermissionEntity createPermission = PermissionEntity.builder()
                    .name(PermissionEnum.CREATE.getValor())
                    .build();

            PermissionEntity readPermission = PermissionEntity.builder()
                    .name(PermissionEnum.READ.getValor())
                    .build();

            PermissionEntity updatePermission = PermissionEntity.builder()
                    .name(PermissionEnum.UPDATE.getValor())
                    .build();

            PermissionEntity deletePermission = PermissionEntity.builder()
                    .name(PermissionEnum.DELETE.getValor())
                    .build();

            /* Create ROLES */
            RoleEntity roleAdmin = RoleEntity.builder()
                    .roleEnum(RoleEnum.ADMIN)
                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                    .build();

            RoleEntity roleUser = RoleEntity.builder()
                    .roleEnum(RoleEnum.USER)
                    .permissionList(Set.of(createPermission, readPermission))
                    .build();

            /* CREATE USERS */
            UserEntity admin = UserEntity.builder()
                    .username(RoleEnum.ADMIN.name().toLowerCase())//"admin"
                    .email("admin@hotmail.com")
                    .password(passwordEncoder.encode(RoleEnum.ADMIN.name().toLowerCase()))//"admin"
                    .isEnable(Boolean.TRUE)
                    .accountNoExpired(Boolean.TRUE)
                    .accountNoLocked(Boolean.TRUE)
                    .credentialNoExpired(Boolean.TRUE)
                    .roles(Set.of(roleAdmin))
                    .build();

            UserEntity user = UserEntity.builder()
                    .username(RoleEnum.USER.name().toLowerCase())//"user"
                    .email("user@hotmail.com")
                    .password(passwordEncoder.encode(RoleEnum.USER.name().toLowerCase()))//"user"
                    .isEnable(Boolean.TRUE)
                    .accountNoExpired(Boolean.TRUE)
                    .accountNoLocked(Boolean.TRUE)
                    .credentialNoExpired(Boolean.TRUE)
                    .roles(Set.of(roleUser))
                    .build();

            UserEntity userCarlos = UserEntity.builder()
                    .username("carlos")
                    .email("carlos@hotmail.com")
                    .password(passwordEncoder.encode("1234"))
                    .isEnable(Boolean.TRUE)
                    .accountNoExpired(Boolean.TRUE)
                    .accountNoLocked(Boolean.TRUE)
                    .credentialNoExpired(Boolean.TRUE)
                    .roles(Set.of(roleAdmin))
                    .build();
            userRepository.saveAll(List.of(admin, user, userCarlos));
        }
    }
}
