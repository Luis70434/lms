package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.api.dto.EnrollmentDtos;
import edu.unimagdalena.lms.entities.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnrollmentMapper {

    Enrollment toEnrollment(EnrollmentDtos.EnrollmentCreateRequest req);

    EnrollmentDtos.EnrollmentResponse toResponse(Enrollment enrollment);
}