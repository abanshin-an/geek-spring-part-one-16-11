package ru.geekbrains;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.commands.Invoker;

public class Main {

    @Autowired
    public Main(Invoker invoker) {
        invoker.executeOperations();
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Invoker invoker=context.getBean(Invoker.class);
        invoker.executeOperations();
    }


}
