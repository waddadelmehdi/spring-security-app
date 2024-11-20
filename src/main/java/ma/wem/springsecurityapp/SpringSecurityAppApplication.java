package ma.wem.springsecurityapp;

import ma.wem.springsecurityapp.entities.Student;
import ma.wem.springsecurityapp.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringSecurityAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
          studentRepository.save(Student.builder()
                          .name("aziz")
                          .email("aziz@gmail.com")
                  .build());
            studentRepository.save(Student.builder()
                    .name("hasan")
                    .email("hasan@gmail.com")
                    .build());
            studentRepository.save(Student.builder()
                    .name("omar")
                    .email("omar@gmail.com")
                    .build());

            studentRepository.findAll().forEach(s->{
                System.out.println("---------------------");
                System.out.println(s.getId());
                System.out.println(s.getName());
                System.out.println(s.getEmail());
                System.out.println("-----------------------");
            });


        };
    }

}
