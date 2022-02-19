package ru.geekbrains.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.service.CartService;
import ru.geekbrains.service.dto.CartDto;
import ru.geekbrains.service.dto.ErrorDto;

import java.util.List;

@RestController
@RequestMapping(value="/api/v1/cart") //, produces = "text/plain;charset=UTF-8")
public class CartResource {

    private static final Logger logger = LoggerFactory.getLogger(CartResource.class);

    private final CartService cartService;

    @Autowired
    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    @ResponseBody
    public List<CartDto> list() {
        return cartService.getAllDto();
    }

    @PutMapping("/{id}")
    public void add(@PathVariable Long id) {
        cartService.addProduct(id,1);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cartService.removeProduct(id,1);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFoundException(NotFoundException ex){
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto illegalArgumentException(IllegalArgumentException ex){
        return new ErrorDto(ex.getMessage());
    }

}
