package com.monolith.platform.learning.web.controller.course;

import com.monolith.platform.learning.domain.dto.course.CourseDTO;
import com.monolith.platform.learning.domain.dto.course.StudentByCourseResponse;
import com.monolith.platform.learning.domain.service.course.AiServiceCourse;
import com.monolith.platform.learning.domain.service.course.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final AiServiceCourse aiServiceCourse;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CourseDTO> saveCourse(@RequestBody @Valid CourseDTO courseDTO){
        CourseDTO dto =  courseService.save(courseDTO);
        return dto != null ?
                ResponseEntity.ok(dto):
                ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<CourseDTO>  updateCourse(@RequestBody @Valid CourseDTO courseDTO){
        CourseDTO dto =  courseService.update(courseDTO);
        return dto != null ?
                ResponseEntity.ok(dto):
                ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Page<CourseDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int elements,
                                                   @RequestParam(defaultValue = "name") String sortBy,
                                                   @RequestParam(defaultValue = "ASC") String sortDirection){
        Page<CourseDTO> courseDTOList = courseService.findAll(page, elements, sortBy, sortDirection);
        return courseDTOList.isEmpty()?
                new  ResponseEntity<>(HttpStatus.NO_CONTENT):
                new ResponseEntity<>(courseDTOList, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CourseDTO> findById(@PathVariable Long id){
        CourseDTO dto =  courseService.findById(id);
        return dto != null ?
                ResponseEntity.ok(dto):
                ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/search-student/{numberCourse}")
    public ResponseEntity<StudentByCourseResponse> findByStudentsAndIdCourse(@PathVariable Long numberCourse){
        StudentByCourseResponse dto =  courseService.findStudentsByNumberCourse(numberCourse);
        return dto != null ?
                ResponseEntity.ok(dto):
                ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id){
        return courseService.deleteById(id) == Boolean.TRUE ?
                ResponseEntity.ok(true):
                ResponseEntity.badRequest().body(false);
    }

    @GetMapping("/explainContent/{numberCourse}")
    public ResponseEntity<String> explainContentCourse(@PathVariable Long numberCourse){
        return ResponseEntity.ok(this.aiServiceCourse.contentCourse(numberCourse));
    }
}
