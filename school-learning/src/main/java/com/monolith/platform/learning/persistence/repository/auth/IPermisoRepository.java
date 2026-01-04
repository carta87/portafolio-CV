package com.monolith.platform.learning.persistence.repository.auth;

import com.monolith.platform.learning.persistence.entity.auth.PermissionEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface IPermisoRepository extends CrudRepository<PermissionEntity, Long> {

    Optional<PermissionEntity> findByName(String name);
}
