package ru.geekbrains.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.dao.ProductDAO;
import ru.geekbrains.entity.Product;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class OperationUpdate implements Operation {
    ProductDAO dao;
    @Autowired
    public OperationUpdate(ProductDAO dao) {
        this.dao=dao;
    }

    @Override
    public String getName() {
        return "UPDATE";
    }

    @Override
    public void execute() {
        Scanner scanner=new Scanner(System.in);
        Product p=new Product();
        System.out.println("enter id:");
        String line = scanner.nextLine().trim();
        if (line.length()>0 && line.matches("\\d")) {
            p.setId(Long.parseLong(line));
        }
        System.out.println("enter name:");
        p.setName(scanner.nextLine().trim());
        System.out.println("enter description:");
        p.setDescription(scanner.nextLine().trim());
        System.out.println("enter price:");
        p.setPrice(new BigDecimal(scanner.nextLine().trim()));

        dao.update(p);
    }
}
