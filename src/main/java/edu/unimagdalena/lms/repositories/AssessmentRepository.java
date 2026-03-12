package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.empities.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssessmentRepository extends JpaRepository<Assessment, UUID> {
}
