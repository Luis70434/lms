package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.api.dto.InstructorProfileDtos;
import edu.unimagdalena.lms.entities.InstructorProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InstructorProfileMapper {

    InstructorProfile toInstructorProfile(InstructorProfileDtos.InstructorProfileCreateRequest req);

    InstructorProfileDtos.InstructorProfileResponse toResponse(InstructorProfile instructorProfile);
}