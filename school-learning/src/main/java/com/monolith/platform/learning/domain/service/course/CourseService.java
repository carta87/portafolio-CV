package com.monolith.platform.learning.domain.service.course;

import com.monolith.platform.learning.domain.dto.course.CourseDTO;
import com.monolith.platform.learning.domain.dto.course.StudentByCourseResponse;
import com.monolith.platform.learning.domain.repository.course.ICourseRepository;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final ICourseRepository iCourseRepository;

    public CourseService(ICourseRepository iCourseRepository){
        this.iCourseRepository = iCourseRepository;
    }

    public CourseDTO save (CourseDTO course){
       return iCourseRepository.save(course);
    }

    public CourseDTO update(CourseDTO course){
       return iCourseRepository.update(course);
    }

    public Page<CourseDTO> findAll(int page, int elements, String sortBy, String sortDirection){
        return iCourseRepository.findAll(page, elements, sortBy, sortDirection);
    }

    @Tool("busca el numero de curso disponible para darle una explicacion del contenido del mismo")
    public CourseDTO findById(Long id){
        return iCourseRepository.findById(id);
    }

    public StudentByCourseResponse findStudentsByNumberCourse(Long numberCourse){
        return iCourseRepository.findStudentsByCourse_NumberCourse(numberCourse);
    }

    public Boolean deleteById(Long id){
        return iCourseRepository.deleteById(id);
    }

}
