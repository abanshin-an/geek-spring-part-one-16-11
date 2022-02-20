package ru.geekbrains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
Урок 10
1. Создать страницу со списком товаров, на которой можно добавлять позиции и редактировать существующие.
   На эту страницу должны иметь доступ админы и менеджеры.

2. Создать страницу со списком всех пользователей, к которой имеют доступ только админы.

3. * Добавить роль суперадмина и дать ему возможность создавать новых пользователей и указывать роли существующим.

Урок 11

+1. Сделать собственную страницу логина, вместо стандартной от Spring Security

 */

@SpringBootApplication
public class Lesson11Security {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(Lesson11Security.class, args);
    }

}
