package com.monolith.platform.learning.domain.repository.course;

import com.monolith.platform.learning.domain.dto.course.CourseDTO;
import com.monolith.platform.learning.domain.dto.course.StudentByCourseResponse;
import com.monolith.platform.learning.domain.repository.IRepositoryCrud;

public interface ICourseRepository extends IRepositoryCrud<CourseDTO> {
    StudentByCourseResponse findStudentsByCourse_NumberCourse(Long numberCourse);
}
