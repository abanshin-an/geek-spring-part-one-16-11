package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        CartService cartService = context.getBean("cartService", CartService.class);
        cartService.add(1L);
        cartService.add(2L);
        cartService.add(3L);
        CartService cartService1 = context.getBean("cartService", CartService.class);
        cartService1.add(1L);
        cartService1.add(2L);
        CartService cartService2 = context.getBean("cartService", CartService.class);
        cartService2.add(3L);

        System.out.println("Product count: " + cartService.list().size());
        System.out.println("Product count: " + cartService1.list().size());
        System.out.println("Product count: " + cartService2.list().size());
    }
}
