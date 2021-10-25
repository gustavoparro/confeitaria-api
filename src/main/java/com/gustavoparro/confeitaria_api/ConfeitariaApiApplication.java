package com.gustavoparro.confeitaria_api;

import com.gustavoparro.confeitaria_api.models.AppUser;
import com.gustavoparro.confeitaria_api.repositories.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConfeitariaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfeitariaApiApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AppUserRepository repository) {
        return args -> repository.save(new AppUser(null, "Gustavo Parro", "gustavo@gmail.com", "123456", null));
    }

}
