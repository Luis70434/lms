package edu.unimagdalena.lms.api.dto;

import java.util.UUID;

public class InstructorProfileDtos {


    public record InstructorProfileCreateRequest(
            String phone,
            String bio,
            UUID instructorId
    ) {}


    public record InstructorProfileResponse(
            UUID id,
            String phone,
            String bio
    ) {}
}