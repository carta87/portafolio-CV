package com.monolith.platform.learning.persistence.repository.course;

import com.monolith.platform.learning.persistence.entity.course.CourseEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICursoPaguinationRepository extends PagingAndSortingRepository<CourseEntity, Long> {

}
