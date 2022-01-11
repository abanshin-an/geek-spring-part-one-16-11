package ru.geekbrains.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.dao.ProductDAO;

import java.util.Scanner;

@Component
public class OperationDelete implements Operation {
    ProductDAO dao;
    @Autowired
    public OperationDelete(ProductDAO dao) {
        this.dao=dao;
    }
    @Override
    public String getName() {
        return "DELETE";
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter id:");
        String line = scanner.nextLine().trim();
        dao.delete(Long.parseLong(line));
    }
}
