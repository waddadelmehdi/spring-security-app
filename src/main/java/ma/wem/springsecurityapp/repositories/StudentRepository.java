package ma.wem.springsecurityapp.repositories;

import ma.wem.springsecurityapp.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Student findStudentById(Long id);
}
