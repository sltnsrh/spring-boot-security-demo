package com.example.demospringsecurity;

import com.example.demospringsecurity.model.Role;
import com.example.demospringsecurity.model.User;
import com.example.demospringsecurity.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringSecurityApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            Role adminRole = userService.saveRole(new Role(Role.RoleName.ADMIN));
            Role userRole = userService.saveRole(new Role(Role.RoleName.USER));
            User bob = new User();
            bob.setUserName("bob@gmail.com");
            bob.getRoles().add(adminRole);
            bob.setPassword(passwordEncoder().encode("password1"));
            userService.save(bob);
            User alice = new User();
            alice.setUserName("alice@gmail.com");
            alice.getRoles().add(userRole);
            alice.setPassword(passwordEncoder().encode("password2"));
            userService.save(alice);
            User john = new User();
            john.setUserName("john@gmail.com");
            john.getRoles().add(adminRole);
            john.getRoles().add(userRole);
            john.setPassword(passwordEncoder().encode("password3"));
            userService.save(john);
        };
    }

}
