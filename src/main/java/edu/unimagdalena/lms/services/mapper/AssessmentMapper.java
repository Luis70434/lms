package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.api.dto.AssessmentDtos;
import edu.unimagdalena.lms.entities.Assessment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AssessmentMapper {

    Assessment toAssessment(AssessmentDtos.AssessmentCreateRequest req);

    AssessmentDtos.AssessmentResponse toResponse(Assessment assessment);
}