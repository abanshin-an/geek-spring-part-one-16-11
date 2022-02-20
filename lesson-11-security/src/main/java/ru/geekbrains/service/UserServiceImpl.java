package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.entity.Product;
import ru.geekbrains.entity.User;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.ProductSpecification;
import ru.geekbrains.persist.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> findAll(Optional<String> nameFilter, Integer page,
                              Integer size, String sort) {
        Specification<User> spec = Specification.where(null);
        return userRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sort)));

    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        User saved = userRepository.save(user);
        return user; //convertToDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
