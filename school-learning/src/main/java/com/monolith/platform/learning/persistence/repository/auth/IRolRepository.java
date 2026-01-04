package com.monolith.platform.learning.persistence.repository.auth;

import com.monolith.platform.learning.persistence.entity.auth.RoleEntity;
import com.monolith.platform.learning.util.enums.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface IRolRepository extends CrudRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRoleEnum(RoleEnum roleEnum);
}
