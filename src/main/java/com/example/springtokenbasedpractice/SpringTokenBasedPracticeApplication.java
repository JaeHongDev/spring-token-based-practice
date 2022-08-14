package com.example.springtokenbasedpractice;

import com.example.springtokenbasedpractice.model.AppUser;
import com.example.springtokenbasedpractice.model.AppUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;


@RequiredArgsConstructor
@SpringBootApplication
public class SpringTokenBasedPracticeApplication implements CommandLineRunner {

    private final UserService userService;
    public static void main(String[] args) {
        SpringApplication.run(SpringTokenBasedPracticeApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        AppUser admin = new Appuser();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@email.com");
        admin.setAppUserRoles(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_ADMIN)));


    }
}
