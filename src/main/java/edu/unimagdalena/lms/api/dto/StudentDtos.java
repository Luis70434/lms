package edu.unimagdalena.lms.api.dto;

import java.time.Instant;
import java.util.UUID;

public class StudentDtos {

    public record StudentCreateRequest(
            String email,
            String fullName
    ) {}

    public record StudentResponse(
            UUID id,
            String email,
            String fullName,
            Instant createdAt,
            Instant updatedAt
    ) {}
}