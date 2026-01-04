package com.monolith.platform.learning.persistence;

import com.monolith.platform.learning.domain.dto.course.CourseDTO;
import com.monolith.platform.learning.domain.dto.course.StudentByCourseResponse;
import com.monolith.platform.learning.domain.dto.student.StudentDTO;
import com.monolith.platform.learning.domain.repository.course.ICourseRepository;
import com.monolith.platform.learning.persistence.entity.course.CourseEntity;
import com.monolith.platform.learning.persistence.entity.student.StudentEntity;
import com.monolith.platform.learning.persistence.mapper.CourseMapper;
import com.monolith.platform.learning.persistence.mapper.StudentMapper;
import com.monolith.platform.learning.persistence.repository.course.ICursoPaguinationRepository;
import com.monolith.platform.learning.persistence.repository.course.ICursoRepository;
import com.monolith.platform.learning.persistence.repository.student.IEstudianteEntityRepository;
import com.monolith.platform.learning.util.ConstantCourse;
import com.monolith.platform.learning.util.ConstantGeneral;
import com.monolith.platform.learning.util.exception.ExceptionUtil;
import com.monolith.platform.learning.util.exception.exceptiongeneric.DuplicateNumberIdEntityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class CourseEntityRepository implements ICourseRepository {

    private final CourseMapper courseMapper;
    private final StudentMapper studentMapper;
    private final ICursoRepository iCursoRepository;
    private final IEstudianteEntityRepository iEstudianteEntityRepository;
    private final ICursoPaguinationRepository iCursoPaguinationRepository;

    public CourseEntityRepository(CourseMapper courseMapper,
                                  StudentMapper studentMapper,
                                  ICursoRepository iCursoRepository,
                                  IEstudianteEntityRepository iEstudianteEntityRepository,
                                  ICursoPaguinationRepository iCursoPaguinationRepository) {
        this.courseMapper = courseMapper;
        this.studentMapper = studentMapper;
        this.iEstudianteEntityRepository = iEstudianteEntityRepository;
        this.iCursoRepository = iCursoRepository;
        this.iCursoPaguinationRepository = iCursoPaguinationRepository;
    }

    @Override
    @Transactional
    public CourseDTO save(CourseDTO courseDTO) {
        if (courseDTO.id() != null && iCursoRepository.existsById(courseDTO.id())) {
            throw new DuplicateNumberIdEntityException(String.format(
                    ConstantGeneral.FIELD_NOT_REQUIRED, + courseDTO.id(), ConstantGeneral.ID),
                    HttpStatus.BAD_REQUEST);
        }
        if (courseDTO.numberCourse() != null && iCursoRepository.existsByNumberCourse(courseDTO.numberCourse())) {
            throw new DuplicateNumberIdEntityException(String.format(
                    ConstantCourse.ERROR_NUM_CURSE_REGISTERED, + courseDTO.numberCourse()),
                    HttpStatus.BAD_REQUEST);
        }
        return courseMapper.mapToDto(
                iCursoRepository.save(courseMapper.mapToEntity(courseDTO)));
    }

    @Override
    @Transactional
    public CourseDTO update(CourseDTO courseDTO) {
        ExceptionUtil.getNotFieldEntityException(courseDTO.id(),
                String.format(ConstantGeneral.ERROR_FIELD_NULL, ConstantGeneral.ID, courseDTO.id()), HttpStatus.BAD_REQUEST);
        CourseEntity  courseEntity = iCursoRepository.findById(courseDTO.id()).
                orElseThrow(()-> new RuntimeException(ConstantCourse.ERROR_COURSE_NOT_REGISTERED));

        CourseEntity updateCourseEntity = courseMapper.mapToEntity(courseDTO);
        updateCourseEntity.setId(courseEntity.getId());

        return courseMapper.mapToDto(iCursoRepository.save(courseMapper.mapToEntity(courseDTO)));
    }

    @Override
    public Page<CourseDTO> findAll(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        return iCursoPaguinationRepository
                .findAll(PageRequest.of(page, elements, sort))
                .map(courseMapper::mapToDto);
    }

    @Override
    public CourseDTO findById(Long id) {
        ExceptionUtil.getIllegalArgumentException(id, ConstantCourse.ERROR_ID_REQUIRED);
        return courseMapper.mapToDto(iCursoRepository.findById(id).orElse(null));
    }

    @Override
    public StudentByCourseResponse findStudentsByCourse_NumberCourse(Long numberCourse) {

        //busqueda de los datos del curso
        CourseEntity courseEntity = iCursoRepository.findByNumberCourse(numberCourse).orElse(new CourseEntity());

        //obtener los estudiantes del curso
        List<StudentEntity> students = iEstudianteEntityRepository.findAllByCourse_NumberCourse(numberCourse);

        //Convertir Entity → DTO
        List<StudentDTO> studentDTOs = students.stream()
                .map(studentMapper::mapToDto)
                .toList();

        //construir el response
        return courseEntity.getNumberCourse() != null ?StudentByCourseResponse.builder()
                .numberCourse(String.valueOf(courseEntity.getNumberCourse()))
                .courseName(courseEntity.getName())
                .teacher(courseEntity.getTeacher() == null ?
                        ConstantCourse.TEACHER_NOT_REGISTERED :
                        courseEntity.getTeacher())
                .studentDTOList(studentDTOs)
                .build() : null;
    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        boolean existsByNumberCourse = iCursoRepository.findById(id).isPresent();
        if(Boolean.TRUE.equals(existsByNumberCourse)){
            iCursoRepository.deleteById(id);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


}
