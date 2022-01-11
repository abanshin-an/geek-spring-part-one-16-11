package ru.geekbrains.commands;

import org.springframework.stereotype.Component;
import ru.geekbrains.dao.ProductDAO;

import java.util.Scanner;

@Component
public class OperationDelete implements Operation {
    @Override
    public String getName() {
        return "DELETE";
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter id:");
        String line = scanner.nextLine().trim();
        ProductDAO.get().delete(Long.parseLong(line));
    }
}
