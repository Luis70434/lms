package edu.unimagdalena.lms.repositories;


import edu.unimagdalena.lms.empities.Course;
import edu.unimagdalena.lms.empities.Enrollment;
import edu.unimagdalena.lms.empities.Instructor;
import edu.unimagdalena.lms.empities.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentRepositoryTest extends AbstractRepositoryTI {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @BeforeEach
    void setUp() {
        enrollmentRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();
    }

    private Course givenCourse(Instructor instructor) {
        Course course = Course.builder()
                .instructor(instructor)
                .title("Software Engineering")
                .status("ACTIVE")
                .active(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return courseRepository.save(course);
    }

    private Student givenStudent() {
        Student student = new Student();
        student.setEmail("student@test.com");
        student.setFullName("Juan Pérez");
        student.setCreatedAt(Instant.now());
        student.setUpdatedAt(Instant.now());

        return studentRepository.save(student);
    }

    private Instructor givenInstructor() {
        Instructor instructor = new Instructor();
        instructor.setEmail("instructor@test.com");
        instructor.setFullName("Carlos Gómez");
        instructor.setCreatedAt(Instant.now());
        instructor.setUpdatedAt(Instant.now());
        return instructorRepository.save(instructor);
    }

    @Test
    @DisplayName("CRUD - Debe crear un estudiante")
    void shouldCreateStudent() {

        Student student = Student.builder()
                .email("test@email.com")
                .fullName("Maria Gomez")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Student saved = studentRepository.save(student);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEmail()).isEqualTo("test@email.com");
        assertThat(saved.getFullName()).isEqualTo("Maria Gomez");
    }

    @Test
    @DisplayName("CRUD - Debe encontrar estudiante por id")
    void shouldFindStudentById() {

        Student student = givenStudent();

        Optional<Student> found = studentRepository.findById(student.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("student@test.com");
    }

    @Test
    @DisplayName("Query - Debe encontrar estudiantes por courseId")
    void shouldFindStudentsByCourseId() {

        Instructor instructor = givenInstructor();
        Course course = givenCourse(instructor);
        Student student = givenStudent();

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        enrollmentRepository.save(enrollment);

        List<Student> result = studentRepository.findStudentsByCourseId(course.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(student.getId());
    }

    @Test
    @DisplayName("CRUD - Debe eliminar estudiante")
    void shouldDeleteStudent() {

        Student student = givenStudent();
        UUID studentId = student.getId();

        studentRepository.deleteById(studentId);

        Optional<Student> found = studentRepository.findById(studentId);

        assertThat(found).isEmpty();
    }

}