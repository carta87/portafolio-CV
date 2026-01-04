package com.monolith.platform.learning.persistence.repository.auth;

import com.monolith.platform.learning.persistence.entity.auth.UserEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface IUsuarioRepository extends CrudRepository<UserEntity, Long > {

   Optional<UserEntity> findByUsername(String username);
   Optional<UserEntity> findByEmail(String email);
}
