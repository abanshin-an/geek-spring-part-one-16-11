package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.service.dto.CartDto;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {

    private final ProductRepository productRepository;

    private final Map<Long, Integer> productCount;
    private final Map<Long, Product> productCache;
    @Autowired
    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.productCount = new HashMap<>();
        this.productCache = new HashMap<>();
    }

    public void addProduct(long id, int count) {
        if (productCount.containsKey(id)) {
            productCount.merge(id, count, Integer::sum);
        }
        else {
            Product prod = getProductId(id);
            productCount.put(id, count);
            productCache.put(id, prod);

        }
        //productCount.get(prod.getId());
    }

    public void removeProduct(long id, int count) {
//        Product prod = getProductId(id);
//        Integer curr = productCount.get(prod);
        Integer curr = productCount.get(id);
        if (curr <= count) {
            productCount.remove(id);
            productCache.remove(id);
        } else {
            productCount.merge(id, -count, Integer::sum);
        }
    }

    public List<Product> getAll() {
        return new ArrayList<>(productCache.values());
    }

    private Product getProductId(long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product with id not exists"));
    }

    public List<CartDto> getAllDto() {
        return getAll().stream()
                .map(p -> new CartDto(p, productCount.get(p.getId())))
                .sorted(Comparator.comparing(CartDto::getProductName))
                .collect(Collectors.toList());
    }

    public BigDecimal calcTotalAmount(List<CartDto> list) {
        return list.stream().map(CartDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
