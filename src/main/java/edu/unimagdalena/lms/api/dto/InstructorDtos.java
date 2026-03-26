package edu.unimagdalena.lms.api.dto;



import java.time.Instant;
import java.util.UUID;

public class InstructorDtos {


    public record InstructorCreateRequest(
            String email,
            String fullName
    ) {}

    //  (Salida)
    public record InstructorResponse(
            UUID id,
            String email,
            String fullName,
            Instant createdAt,
            Instant updatedAt
    ) {}
}