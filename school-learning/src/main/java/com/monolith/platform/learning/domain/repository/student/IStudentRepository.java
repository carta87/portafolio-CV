package com.monolith.platform.learning.domain.repository.student;

import com.monolith.platform.learning.domain.dto.student.StudentDTO;
import com.monolith.platform.learning.domain.repository.IRepositoryCrud;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface IStudentRepository extends IRepositoryCrud<StudentDTO> {

    List<StudentDTO> findByIdCourse(Long idCourse);

    ResponseEntity<byte[]> genericReport(StudentDTO studentDTO );
}
