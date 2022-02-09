package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductSpecification;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.dto.ProductDto;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductController(ProductService productService,
                             CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    /**
     * получение всех товаров [ GET .../app/products ]
     * @param model
     * @param nameFilter
     * @param page
     * @param size
     * @param sort
     * @return
     */
    @GetMapping
    public String listPage(Model model,
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
            spec=spec.and(ProductSpecification.nameLike(nameFilter.get()));
        }
        if (minPriceFilter.isPresent() && !minPriceFilter.get().isBlank()) {
            spec=spec.and(ProductSpecification.minPriceFilter(new BigDecimal(minPriceFilter.get())));
        }
        if (maxPriceFilter.isPresent() && !maxPriceFilter.get().isBlank()) {
            spec=spec.and(ProductSpecification.maxPriceFilter(new BigDecimal(maxPriceFilter.get())));
        }
        String field=sort.filter(s -> !s.isBlank()).orElse("id");
        boolean sortDir = dir.orElse("").equalsIgnoreCase(Sort.Direction.ASC.name());
        Sort sortObj=sortDir ? Sort.by(field).ascending(): Sort.by(field).descending();
        model.addAttribute("products", productService.findAll(
                spec,
                page.orElse(1) - 1,
                size.orElse(5),
                sortObj
        ));
        return "product";
    }

    /**
     * получение товара по id [ GET .../app/products/{id} ]
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found")));
        model.addAttribute("categories", categoryRepository.findAll());
        return "product_form";
    }

    /**
     * создание нового товара [ POST .../app/products ]
     * @param model
     * @return
     */
    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryRepository.findAll());
        return "product_form";
    }

    @PostMapping
    public String save(@Valid ProductDto product, BindingResult result) {
        if (result.hasErrors()) {
            return "product_form";
        }
        productService.save(product);
        return "redirect:/product";
    }
    /**
     * удаление товара по id.[ GET .../app/products/delete/{id} ]
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/product";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundExceptionHandler(NotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "not_found";
    }
}

