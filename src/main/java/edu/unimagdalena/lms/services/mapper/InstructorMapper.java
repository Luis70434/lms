package edu.unimagdalena.lms.services.mapper;


import edu.unimagdalena.lms.api.dto.InstructorDtos.InstructorCreateRequest;
import edu.unimagdalena.lms.api.dto.InstructorDtos.InstructorResponse;
import edu.unimagdalena.lms.entities.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstructorMapper {
    Instructor toEntity(InstructorCreateRequest req);
    InstructorResponse toResponse(Instructor entity);
}