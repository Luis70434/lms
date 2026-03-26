
package edu.unimagdalena.lms.api.dto;

import java.time.Instant;
import java.util.UUID;

public class CourseDtos {


    public record CourseCreateRequest(
            String title,
            String status,
            Boolean active
    ) {}


    public record CourseResponse(
            UUID id,
            String title,
            String status,
            Boolean active,
            Instant createdAt,
            Instant updatedAt
    ) {}
}