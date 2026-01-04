package com.monolith.platform.learning.domain.service.student;

import com.monolith.platform.learning.domain.dto.student.StudentDTO;
import com.monolith.platform.learning.domain.repository.student.IStudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {
    private final IStudentRepository iStudentRepository;

    public StudentService(IStudentRepository iStudentRepository) {
        this.iStudentRepository = iStudentRepository;
    }

    public StudentDTO save(StudentDTO studentDTO){
        return iStudentRepository.save(studentDTO);
    }

    public StudentDTO update(StudentDTO studentDTO){
        return iStudentRepository.update(studentDTO);
    }

    public Page<StudentDTO> findAll(int page, int elements, String sortBy, String sortDirection){
        return iStudentRepository.findAll(page, elements, sortBy, sortDirection);
    }

    public StudentDTO findById(Long id){
        return iStudentRepository.findById(id);
    }

    public ResponseEntity<byte[]> genericReport(StudentDTO studentDTO){
        return iStudentRepository.genericReport(studentDTO);
    }

    public List<StudentDTO> findByIdCourse(Long idCourse){
        return iStudentRepository.findByIdCourse(idCourse);
    }

    public Boolean delete(Long id){
        return iStudentRepository.deleteById(id);
    }
}
