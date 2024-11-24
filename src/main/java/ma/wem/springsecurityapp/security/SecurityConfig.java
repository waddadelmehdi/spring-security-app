package ma.wem.springsecurityapp.security;

import ma.wem.springsecurityapp.auth.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static ma.wem.springsecurityapp.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder;

        this.applicationUserService = applicationUserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .csrf(csrf -> csrf
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/index.html", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/api/**").hasRole(STUDENT.name())
//                        .requestMatchers("/management/api/**").hasRole(STUDENT.name())
//                        .requestMatchers("/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRENE.name())
                        .anyRequest().authenticated()

                )
                .formLogin(
                        (form -> form
                                .loginPage("/login")
                                .defaultSuccessUrl("/services",true)
                                .passwordParameter("password")
                                .usernameParameter("username")
                                .permitAll()))
                .rememberMe(remember -> remember
                        .key("verysec")
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                        .rememberMeParameter("remember-me") // Name of the checkbox parameter in the login form
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .logoutSuccessUrl("/login")
                );

                //.formLogin(Customizer.withDefaults());

        return http.build();
    }


    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(applicationUserService);
        return authProvider;
    }
//    public UserDetailsService userDetailsService() {
//        UserDetails userMehdi = User.builder()
//                .username("mehdi")
//                .password(passwordEncoder.encode("12345"))
//                .roles(STUDENT.name())
//                .authorities(STUDENT.getGrantedAuthorities())
//                .build();
//        UserDetails adminMehdi = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("admin12345"))
//                .roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//        UserDetails adminMehdi1 = User.builder()
//                .username("admin1")
//                .password(passwordEncoder.encode("admin12345"))
//                .roles(ADMINTRENE.name())
//                .authorities(ADMINTRENE.getGrantedAuthorities())
//                .build();
//        return new InMemoryUserDetailsManager(
//                userMehdi
//                , adminMehdi,
//                adminMehdi1
//        );
//    }

}
