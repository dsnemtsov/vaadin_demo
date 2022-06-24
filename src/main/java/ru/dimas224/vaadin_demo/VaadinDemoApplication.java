package ru.dimas224.vaadin_demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.dimas224.vaadin_demo.domain.UserEntity;
import ru.dimas224.vaadin_demo.repo.UserRepo;

@SpringBootApplication
public class VaadinDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(VaadinDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(UserRepo repo) {
        return args -> {
            repo.save(UserEntity.builder()
                    .lastName("Иванов")
                    .firstName("Иван")
                    .patronymic("Иванович").build());
            repo.save(UserEntity.builder()
                    .lastName("Петров")
                    .firstName("Петр")
                    .patronymic("Петрович").build());
            repo.save(UserEntity.builder()
                    .lastName("Пушкин")
                    .firstName("Александр")
                    .patronymic("Сергеевич").build());
        };
    }
}
