package ru.geekbrains.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.dao.ProductDAO;
import ru.geekbrains.entity.Product;

import java.util.List;

@Component
public class OperationList implements Operation {
    ProductDAO dao;
    @Autowired
    public OperationList(ProductDAO dao) {
        this.dao=dao;
    }
    @Override
    public String getName() {
        return "LIST";
    }

    @Override
    public void execute() {
        List<Product> productList=dao.getAll();
        productList.forEach(System.out::println);
    }
}
