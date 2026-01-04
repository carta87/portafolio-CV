package com.monolith.platform.learning.persistence.repository.student;

import com.monolith.platform.learning.persistence.entity.student.StudentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IEstudianteEntityRepository extends CrudRepository<StudentEntity, Long> {

    List<StudentEntity> findAllByCourse_NumberCourse(Long courseNumber);

    @Query("""
    SELECT s
    FROM StudentEntity s
    WHERE s.course.numberCourse = :courseNumber""")
    List<StudentEntity> findAllStudent(@Param("courseNumber") Long courseNumber);
}
