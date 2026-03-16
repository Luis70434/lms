package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Query("""
    SELECT DISTINCT s
    FROM Student s
    JOIN s.enrollments e
    WHERE e.course.id = :courseId
""")
    List<Student> findStudentsByCourseId(UUID courseId);
}
