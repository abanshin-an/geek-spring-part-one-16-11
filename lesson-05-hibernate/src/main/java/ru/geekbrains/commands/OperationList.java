package ru.geekbrains.commands;

import org.springframework.stereotype.Component;
import ru.geekbrains.dao.ProductDAO;
import ru.geekbrains.entity.Product;

import java.util.List;

@Component
public class OperationList implements Operation {
    @Override
    public String getName() {
        return "LIST";
    }

    @Override
    public void execute() {
        List<Product> productList=ProductDAO.get().getAll();
        productList.forEach(System.out::println);
    }
}
