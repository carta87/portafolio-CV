package com.monolith.platform.learning.persistence.repository.student;

import com.monolith.platform.learning.persistence.entity.student.StudentEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IEstudiantePaguinationRepository extends PagingAndSortingRepository<StudentEntity, Long> {
}
