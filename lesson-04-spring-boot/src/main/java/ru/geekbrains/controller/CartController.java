package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.persist.CartService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String listPage(Model model) {
        model.addAttribute("cart", cartService.getAllDTO());
        return "cart";
    }
    @GetMapping("cart/add")
    public String add(HttpServletRequest request, Model model, @RequestParam(name = "id") Long productId) {
        cartService.addProduct(productId,1);
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl).orElse("/");
    }
    @GetMapping("cart/remove")
    public String delete(HttpServletRequest request, Model model,@RequestParam(name = "id") Long productId) {
        cartService.removeProduct(productId,1);
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl).orElse("/");
    }
}
