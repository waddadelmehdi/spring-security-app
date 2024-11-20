package ma.wem.springsecurityapp.web;

import ma.wem.springsecurityapp.entities.Student;
import ma.wem.springsecurityapp.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("management/api/v1/school")
public class StudentManagementController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRENE')")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping("/addStudent")
    @PreAuthorize("hasAuthority('student:write')")
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }
    @PutMapping("/updateStudent")
    @PreAuthorize("hasAuthority('student:write')")
    public Student updateStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }
    @DeleteMapping("/deleteStudent/{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}
