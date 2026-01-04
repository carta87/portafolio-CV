package com.monolith.platform.learning.persistence;

import com.monolith.platform.learning.domain.dto.student.StudentDTO;
import com.monolith.platform.learning.domain.repository.student.IStudentRepository;
import com.monolith.platform.learning.persistence.entity.student.StudentEntity;
import com.monolith.platform.learning.persistence.mapper.StudentMapper;
import com.monolith.platform.learning.persistence.repository.course.ICursoRepository;
import com.monolith.platform.learning.persistence.repository.student.IEstudianteEntityRepository;
import com.monolith.platform.learning.persistence.repository.student.IEstudiantePaguinationRepository;
import com.monolith.platform.learning.util.ConstantCourse;
import com.monolith.platform.learning.util.ConstantGeneral;
import com.monolith.platform.learning.util.ConstantStudent;
import com.monolith.platform.learning.util.exception.ExceptionUtil;
import com.monolith.platform.learning.util.exception.exceptiongeneric.DuplicateNumberIdEntityException;
import com.monolith.platform.learning.util.exception.exceptiongeneric.IdNotRequiredEntityException;
import com.monolith.platform.learning.util.exception.exceptiongeneric.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentEntityRepository implements IStudentRepository {

    private final StudentMapper studentMapper;
    private final ICursoRepository iCursoRepository;
    private final ReportEntityRepository reportEntityRepository;
    private final IEstudianteEntityRepository iEstudianteEntityRepository;
    private final IEstudiantePaguinationRepository iEstudiantePaguinationRepository;

    public StudentEntityRepository(StudentMapper studentMapper, ICursoRepository iCursoRepository,
                                   ReportEntityRepository reportEntityRepository,
                                   IEstudianteEntityRepository iEstudianteEntityRepository, IEstudiantePaguinationRepository iEstudiantePaguinationRepository) {
        this.studentMapper = studentMapper;
        this.iCursoRepository = iCursoRepository;
        this.reportEntityRepository = reportEntityRepository;
        this.iEstudianteEntityRepository = iEstudianteEntityRepository;
        this.iEstudiantePaguinationRepository = iEstudiantePaguinationRepository;
    }

    @Override
    @Transactional
    public StudentDTO save(StudentDTO studentDTO) {
        if(studentDTO.id() != null && iEstudianteEntityRepository.existsById(studentDTO.id())){
            throw new DuplicateNumberIdEntityException(String.format(
                    ConstantStudent.ERROR_STUDENT_REGISTERED, studentDTO.id(), ConstantGeneral.ID),
                    HttpStatus.BAD_REQUEST);
        }
        if(studentDTO.attendant().id() != null ){
            throw new IdNotRequiredEntityException(
                    ConstantStudent.ERROR_ID_ATTENDANT_REQUIRED,
                    HttpStatus.BAD_REQUEST);
        }
        validInfoCourseNumber(studentDTO);

        StudentEntity savedEntity = iEstudianteEntityRepository.save(
                studentMapper.mapToEntity(studentDTO));

        return studentMapper.mapToDto(savedEntity);
    }

    private void validInfoCourseNumber(StudentDTO studentDTO) {
        ExceptionUtil.getNotFieldEntityException(studentDTO.courseDTO().numberCourse(),
                String.format(ConstantGeneral.ERROR_FIELD_NULL, ConstantCourse.NUMBER_COURSE, studentDTO.id()), HttpStatus.BAD_REQUEST);
        if(iCursoRepository.findByNumberCourse(studentDTO.courseDTO().numberCourse()).isEmpty()){
            throw new NotFoundException(
                    ConstantCourse.ERROR_COURSE_NOT_REGISTERED, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public StudentDTO update(StudentDTO studentDTO) {
        ExceptionUtil.getNotFieldEntityException(studentDTO.id(),
                String.format(ConstantGeneral.ERROR_FIELD_NULL, ConstantGeneral.ID, studentDTO.id()), HttpStatus.BAD_REQUEST);
        StudentEntity studentEntity = iEstudianteEntityRepository.findById(studentDTO.id())
                .orElseThrow(() -> new RuntimeException(ConstantStudent.ERROR_STUDENT_NOT_REGISTERED));

        StudentEntity updatedEntity = studentMapper.mapToEntity(studentDTO);
        updatedEntity.setId(studentEntity.getId());

        StudentEntity savedEntity = iEstudianteEntityRepository.save(updatedEntity);

        return studentMapper.mapToDto(savedEntity);
    }

    @Override
    public Page<StudentDTO> findAll(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, elements, sort);
        return iEstudiantePaguinationRepository.findAll(pageable)
                .map(studentMapper::mapToDto);
    }

    @Override
    public StudentDTO findById(Long id) {
        ExceptionUtil.getIllegalArgumentException(id, ConstantStudent.ERROR_ID_REQUIRED);
        return studentMapper.mapToDto(iEstudianteEntityRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public  ResponseEntity<byte[]> genericReport(StudentDTO studentDTO ) {
        StudentEntity savedEntity = iEstudianteEntityRepository.save(
                studentMapper.mapToEntity(studentDTO));

        StudentDTO savedStudentDTO = studentMapper.mapToDto(savedEntity);

        return reportEntityRepository.reporteComprobante(savedStudentDTO);
    }

    @Override
    public List<StudentDTO> findByIdCourse(Long idCourse) {
        ExceptionUtil.getIllegalArgumentException(idCourse, ConstantCourse.ERROR_ID_REQUIRED);
        return iEstudianteEntityRepository.findAllStudent(idCourse)
                .stream()
                .map(studentMapper::mapToDto)
                .toList();
    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        Optional<StudentEntity> existingStudent = iEstudianteEntityRepository.findById(id);
        if (existingStudent.isPresent()) {
            iEstudianteEntityRepository.delete(existingStudent.get());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
