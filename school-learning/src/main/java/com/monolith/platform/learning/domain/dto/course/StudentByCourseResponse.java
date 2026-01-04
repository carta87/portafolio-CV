package com.monolith.platform.learning.domain.dto.course;

import com.monolith.platform.learning.domain.dto.student.StudentDTO;
import lombok.Builder;
import java.util.List;

@Builder
public record StudentByCourseResponse(
        String numberCourse,
        String courseName,
        String teacher,
        List<StudentDTO> studentDTOList) {
}