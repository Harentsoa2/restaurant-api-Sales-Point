package edu.hei.school.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishCreation {
    @JsonProperty("id")
    private Long idDish;
    private String name;
    private double unitPrice;

    @JsonCreator
    public DishCreation(Long idDish, String name, double unitPrice) {
        this.idDish = idDish;
        this.name = name;
        this.unitPrice = unitPrice;
    }
}
