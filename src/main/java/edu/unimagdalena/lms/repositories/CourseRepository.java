package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.empities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    @Query("""
    SELECT DISTINCT c
    FROM Course c
    JOIN c.enrollments e
    WHERE e.student.id = :studentId
""")
    List<Course> findCoursesByStudentId(@Param("studentId") UUID studentId);

    List<Course> findByInstructor_IdAndActiveTrue(UUID instructorId);
}
