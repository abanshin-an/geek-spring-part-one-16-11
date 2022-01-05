package ru.geekbrains.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {

    private final ProductRepository productRepository;

    private final Map<Product, Integer> productCount;

    @Autowired
    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.productCount = new HashMap<>();
    }

    public void addProduct(long id, int count) {
        Product prod = getProductId(id);
        productCount.merge(prod, count, Integer::sum);
    }

    public void removeProduct(long id, int count) {
        Product prod = getProductId(id);
        Integer curr = productCount.get(prod);
        if (curr <= count) {
            productCount.remove(prod);
        } else {
            productCount.merge(prod, -count, Integer::sum);
        }
    }

    public List<Product> getAll() {
        return new ArrayList<>(productCount.keySet());
    }

    private Product getProductId(long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product with id not exists"));
    }

    public List<CartDTO> getAllDTO() {
        return getAll().stream()
                .map(p -> new CartDTO(p, productCount.get(p)))
                .sorted(Comparator.comparing(CartDTO::getProductName))
                .collect(Collectors.toList());
    }
}
