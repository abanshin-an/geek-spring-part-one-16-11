package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.entity.User;
import ru.geekbrains.service.dto.ProductDto;

import java.util.Optional;

public interface UserService {
    Page<User> findAll(Optional<String> nameFilter, Integer page, Integer size, String sort);

    Optional<User> findById(Long id);

    User save(User product);

    void deleteById(Long id);
}
