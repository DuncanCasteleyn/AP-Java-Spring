package com.infosupport.ap.exercise;

import com.infosupport.ap.exercise.repositories.ClassRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Program {
    public static void main(String[] args) {
        SpringApplication.run(Program.class);
    }

    @Autowired
    ClassRegistrationRepository repo;

    @Bean
    CommandLineRunner runAtStartup() {
        return args -> {
            System.out.println("I have started succesfully. You can open the Front-end on localhost:8080 and start your exercise.");
        };
    }
}
