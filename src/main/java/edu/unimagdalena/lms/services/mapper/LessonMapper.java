package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.api.dto.LessonDtos;
import edu.unimagdalena.lms.entities.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LessonMapper {

    Lesson toLesson(LessonDtos.LessonCreateRequest req);

    LessonDtos.LessonResponse toResponse(Lesson lesson);
}