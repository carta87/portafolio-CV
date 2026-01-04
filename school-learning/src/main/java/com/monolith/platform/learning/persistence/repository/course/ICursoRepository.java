package com.monolith.platform.learning.persistence.repository.course;

import com.monolith.platform.learning.persistence.entity.course.CourseEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface ICursoRepository extends CrudRepository<CourseEntity, Long> {

    Optional<CourseEntity> findByNumberCourse(Long numberCourse);

    boolean existsByNumberCourse(Long numberCourse);
}
