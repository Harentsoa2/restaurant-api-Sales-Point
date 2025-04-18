package edu.hei.school.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class Dish {
    private Long idDish;
    private String name;
    private int unitPrice;
    private List<RequiredIngredient> requiredIngredients;

    public Dish(Long idDish, String name, int unitPrice, List<RequiredIngredient> requiredIngredients) {
        this.idDish = idDish;
        this.name = name;
        this.unitPrice = unitPrice;
        this.requiredIngredients = requiredIngredients;
    }

    @JsonIgnore
    public Double getAvailableQuantity(LocalDateTime date){

        return this.getRequiredIngredients().stream()
                .mapToDouble(ri -> ri.getIngredient().getAvailableQuantity(date).doubleValue() / ri.getQuantity())
                .min()
                .orElse(0);

    }

    public Double getAvailableQuantity() {
        return getAvailableQuantity(LocalDateTime.now());
    }



    public Dish() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return unitPrice == dish.unitPrice && Objects.equals(idDish, dish.idDish) && Objects.equals(name, dish.name) && Objects.equals(requiredIngredients, dish.requiredIngredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDish, name, unitPrice, requiredIngredients);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "idDish=" + idDish +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", requiredIngredients=" + requiredIngredients +
                '}';
    }
}
