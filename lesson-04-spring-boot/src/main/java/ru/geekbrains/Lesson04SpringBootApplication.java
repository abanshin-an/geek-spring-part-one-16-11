package ru.geekbrains;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import ru.geekbrains.persist.CartService;
import ru.geekbrains.persist.ProductRepository;

@SpringBootApplication
@Slf4j
public class Lesson04SpringBootApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        log.info("SpringApplicationBuilder configure");
        return application.sources(Lesson04SpringBootApplication.class);
    }

    public static void main(String[] args) {
        log.info("App mvc-app start");
        SpringApplication.run(Lesson04SpringBootApplication.class, args);
    }
}
