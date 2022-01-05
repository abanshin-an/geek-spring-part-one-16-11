package ru.geekbrains.persist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    @DecimalMin("0.01")
    private BigDecimal price;

}
