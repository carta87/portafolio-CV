package com.monolith.platform.learning.web.controller.student;

import com.monolith.platform.learning.domain.dto.student.StudentDTO;
import com.monolith.platform.learning.domain.service.student.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("save/createComprobante")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<byte[]> saveStudentCreateComprobante(@RequestBody @Valid StudentDTO studentDTO){
       return studentService.genericReport(studentDTO);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody @Valid StudentDTO studentDTO){
        StudentDTO studentResponseDTO = studentService.save(studentDTO);
        return studentResponseDTO != null ?
                ResponseEntity.ok(studentResponseDTO):
                ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody @Valid StudentDTO studentDTO){
        StudentDTO studentResponseDTO = studentService.update(studentDTO);
        return studentResponseDTO != null ?
                ResponseEntity.ok(studentResponseDTO):
                ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Page<StudentDTO>> findAllStudents(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int elements,
                                                            @RequestParam(defaultValue = "name") String sortBy,
                                                            @RequestParam(defaultValue = "ASC") String sortDirection){
        return ResponseEntity.ok(studentService.findAll(page, elements, sortBy, sortDirection));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable Long id){
        StudentDTO studentDTO = studentService.findById(id);
        return  studentDTO != null ?
                ResponseEntity.ok(studentDTO) :
                ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/search-by-course/{idCourse}")
    public ResponseEntity<List<StudentDTO>> findByIDCourse(@PathVariable Long idCourse){
        List<StudentDTO> studentDTOList = studentService.findByIdCourse(idCourse);
        return  studentDTOList != null && !studentDTOList.isEmpty() ?
                ResponseEntity.ok(studentDTOList) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id){
        return studentService.delete(id) == Boolean.TRUE ?
                ResponseEntity.ok(true):
                ResponseEntity.badRequest().body(false);
    }
}
