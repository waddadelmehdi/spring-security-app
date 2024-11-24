package ma.wem.springsecurityapp.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ma.wem.springsecurityapp.security.ApplicationUserRole.*;
@Repository("fake")
public class FakeApplicationUserService implements ApplicationUserDao{
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> users = Lists.newArrayList(
                new ApplicationUser(
                        STUDENT.getGrantedAuthorities(),
                        "mehdi",
                        passwordEncoder.encode("12345"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        ADMIN.getGrantedAuthorities(),
                        "admin",
                        passwordEncoder.encode("admin12345"),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        ADMINTRENE.getGrantedAuthorities(),
                        "admin1",
                        passwordEncoder.encode("admin12345"),
                        true,
                        true,
                        true,
                        true
                )

        );

        return users;
    }
}
