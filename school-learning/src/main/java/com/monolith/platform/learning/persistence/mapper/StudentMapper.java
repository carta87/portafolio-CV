package com.monolith.platform.learning.persistence.mapper;

import com.monolith.platform.learning.domain.dto.student.AttendantDTO;
import com.monolith.platform.learning.domain.dto.student.StudentDTO;
import com.monolith.platform.learning.persistence.entity.student.AttendantEntity;
import com.monolith.platform.learning.persistence.entity.student.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",  uses = { CourseMapper.class })
public interface StudentMapper {

    List<StudentDTO> mapToDto(List<StudentEntity> studentEntities);

    @Mapping(source = "course", target = "courseDTO")
    StudentDTO mapToDto(StudentEntity studentEntity);

    @Mapping(source = "courseDTO", target = "course")
    StudentEntity mapToEntity(StudentDTO studentDTO);

    AttendantDTO mapToDto(AttendantEntity attendantEntity);
}
