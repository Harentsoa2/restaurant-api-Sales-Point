package edu.hei.school.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.hei.school.restaurant.entity.Unity;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@JsonPropertyOrder({"requiredQuantity", "unit", "ingredient"})
public class RequiredIngredientRest {
    @JsonProperty("requiredQuantity")
    private Double quantity;
    @JsonProperty("ingredient")
    private IngredientDishRest ingredientRest;

    public RequiredIngredientRest(Double quantity, IngredientDishRest ingredientRest) {
        this.quantity = quantity;
        this.ingredientRest = ingredientRest;
    }

    @JsonProperty("unit")
    public Unity getUnit(){
        return ingredientRest.getUnity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequiredIngredientRest that = (RequiredIngredientRest) o;
        return Objects.equals(quantity, that.quantity) && Objects.equals(ingredientRest, that.ingredientRest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, ingredientRest);
    }

    @Override
    public String toString() {
        return "RequiredIngredientRest{" +
                "quantity=" + quantity +
                ", ingredientRest=" + ingredientRest +
                '}';
    }
}
