package edu.hei.school.restaurant.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class RequiredIngredient {
    private Dish dish;
    private Double quantity;
    private Ingredient ingredient;

    public RequiredIngredient(Dish dish, Double quantity, Ingredient ingredient) {
        this.dish = dish;
        this.quantity = quantity;
        this.ingredient = ingredient;
    }

    public RequiredIngredient(Double quantity, Ingredient ingredient) {
        this.quantity = quantity;
        this.ingredient = ingredient;
    }

    public RequiredIngredient() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequiredIngredient that = (RequiredIngredient) o;
        return Objects.equals(dish, that.dish) && Objects.equals(quantity, that.quantity) && Objects.equals(ingredient, that.ingredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dish, quantity, ingredient);
    }

    @Override
    public String
    toString() {
        return "RequiredIngredient{" +
                "dish=" + dish +
                ", quantity=" + quantity +
                ", ingredient=" + ingredient +
                '}';
    }
}
