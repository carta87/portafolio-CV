package com.monolith.platform.learning.persistence.mapper;

import com.monolith.platform.learning.domain.dto.course.CourseDTO;
import com.monolith.platform.learning.persistence.entity.course.CourseEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    List<CourseDTO> mapToDto(List<CourseEntity> courseEntityList);

    CourseDTO mapToDto(CourseEntity courseEntity);

    CourseEntity mapToEntity(CourseDTO courseDTO);
}
