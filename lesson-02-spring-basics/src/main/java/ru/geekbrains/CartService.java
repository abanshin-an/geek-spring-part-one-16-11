package ru.geekbrains;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;

    List<Product> productList=new ArrayList<>();

    public void add(Long id) {
        productList.add(productRepository.findById(id));
    }
    public void remove(Long id) {
        productList.remove(productRepository.findById(id));
    }
    public List<Product> list(){
        return Collections.unmodifiableList(productList);
    }
}
