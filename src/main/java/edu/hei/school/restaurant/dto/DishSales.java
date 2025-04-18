package edu.hei.school.restaurant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishSales {
    private Long dishIdentifier;
    private String dishName;
    private Integer quantitySold;

    public DishSales(Long dishIdentifier, String dishName, Integer quantitySold) {
        this.dishIdentifier = dishIdentifier;
        this.dishName = dishName;
        this.quantitySold = quantitySold;
    }


}
