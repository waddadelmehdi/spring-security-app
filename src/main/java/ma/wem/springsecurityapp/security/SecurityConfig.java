package ma.wem.springsecurityapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static ma.wem.springsecurityapp.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .csrf(csrf -> csrf
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/index.html", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/api/**").hasRole(STUDENT.name()) // Public API access
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/index.html", true) // Redirect to index.html after login
                        .permitAll() // Allow access to the default login page
                )
                .httpBasic(Customizer.withDefaults()); // Optional: Enable basic auth for APIs if needed
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userMehdi = User.builder()
                .username("mehdi")
                .password(passwordEncoder.encode("12345"))
                .roles(STUDENT.name())
                .authorities(STUDENT.getGrantedAuthorities())
                .build();
        UserDetails adminMehdi = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin12345"))
                .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        UserDetails adminMehdi1 = User.builder()
                .username("admin1")
                .password(passwordEncoder.encode("admin12345"))
                .roles(ADMINTRENE.name())
                .authorities(ADMINTRENE.getGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(
                userMehdi
                , adminMehdi,
                adminMehdi1
        );
    }

}
