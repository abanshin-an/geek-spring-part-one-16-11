package ru.geekbrains.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.controller.ProductController;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductSpecification;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.dto.ErrorDto;
import ru.geekbrains.service.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping(value="/api/v1/product") //, produces = "text/plain;charset=UTF-8")
public class ProductResource {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseBody
    public Page<ProductDto> search(
            @RequestParam("nameFilter") Optional<String> nameFilter,
            @RequestParam("minPriceFilter") Optional<String> minPriceFilter,
            @RequestParam("maxPriceFilter") Optional<String> maxPriceFilter,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("dir") Optional<String> dir
    ) {
        logger.info("Product filter with name pattern {}", nameFilter.orElse(null));
        Specification<Product> spec = Specification.where(null);
        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            spec = spec.and(ProductSpecification.nameLike(nameFilter.get()));
        }
        if (minPriceFilter.isPresent() && !minPriceFilter.get().isBlank()) {
            spec = spec.and(ProductSpecification.minPriceFilter(new BigDecimal(minPriceFilter.get())));
        }
        if (maxPriceFilter.isPresent() && !maxPriceFilter.get().isBlank()) {
            spec = spec.and(ProductSpecification.maxPriceFilter(new BigDecimal(maxPriceFilter.get())));
        }
        String field = sort.filter(s -> !s.isBlank()).orElse("id");
        boolean sortDir = dir.orElse("").equalsIgnoreCase(Sort.Direction.ASC.name());
        Sort sortObj = sortDir ? Sort.by(field).ascending() : Sort.by(field).descending();
        return productService.findAll(
                spec,
                page.orElse(1) - 1,
                size.orElse(5),
                sortObj
        );
    }

    @GetMapping("/{id}")
    public ProductDto findOne(@PathVariable("id") Long id){
        return productService.findById(id).orElseThrow( () -> new NotFoundException("Product with id " + id + " not found"));
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@RequestBody ProductDto productDto){
        if ( productDto.getId()!=null) {
            throw new IllegalArgumentException("For product creation id have to be null");
        }
        return productService.save(productDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus()
    public ProductDto update(@RequestBody ProductDto productDto){
        if ( productDto.getId()==null) {
            throw new IllegalArgumentException("For product update id have to be not null");
        }
        return productService.save(productDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id ) {
        productService.deleteById(id);
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
//    @PutMapping

}
