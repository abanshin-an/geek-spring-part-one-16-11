package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final String PRODUCT = "product";
    private static final String PRODUCT_FORM = "product_form";
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listPage(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return PRODUCT;
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute(PRODUCT, productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found")));
        return PRODUCT_FORM;
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute(PRODUCT, new Product());
        return PRODUCT_FORM;
    }

    @PostMapping
    public String save(Model model,Product product) {
            productRepository.save(product);
            return "redirect:/"+PRODUCT;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundExceptionHandler(NotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "not_found";
    }
}
