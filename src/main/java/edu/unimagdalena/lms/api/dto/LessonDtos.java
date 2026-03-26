package edu.unimagdalena.lms.api.dto;

import java.util.UUID;

public class LessonDtos {


    public record LessonCreateRequest(
            String title,
            Integer orderIndex,
            UUID courseId
    ) {}


    public record LessonResponse(
            UUID id,
            String title,
            Integer orderIndex
    ) {}
}