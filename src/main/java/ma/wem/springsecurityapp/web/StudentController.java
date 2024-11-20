package ma.wem.springsecurityapp.web;

import ma.wem.springsecurityapp.entities.Student;
import ma.wem.springsecurityapp.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/school")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    @GetMapping(path = "/students/{studentId}")
    public Student getStudent(@PathVariable("studentId") Long studentId) {
        return studentRepository.findStudentById(studentId);
    }
}
