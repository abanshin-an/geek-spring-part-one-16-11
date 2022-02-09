package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.Product;
import ru.geekbrains.service.dto.ProductDto;

import java.util.Optional;

public interface ProductService {

    Page<ProductDto> findAll(Specification<Product> spec, Integer page, Integer size, Sort sort);

    Optional<ProductDto> findById(Long id);

    ProductDto save(ProductDto product);

    void deleteById(Long id);

}
