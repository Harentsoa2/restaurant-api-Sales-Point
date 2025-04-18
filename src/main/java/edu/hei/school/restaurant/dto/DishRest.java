package edu.hei.school.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class DishRest {
    @JsonProperty("id")
    private Long idDish;
    @JsonProperty("name")
    private String name;
    @JsonProperty("actualPrice")
    private int unitPrice;
    @JsonProperty("ingredients")
    private List<RequiredIngredientRest> requiredIngredient;

    public DishRest(Long idDish, String name, int unitPrice, List<RequiredIngredientRest> requiredIngredient) {
        this.idDish = idDish;
        this.name = name;
        this.unitPrice = unitPrice;
        this.requiredIngredient = requiredIngredient;
    }

    @JsonIgnore
    public Double getAvailableQuantity(LocalDateTime date){

        return this.requiredIngredient.stream()
                .mapToDouble(ri -> ri.getIngredientRest().getActualstock().doubleValue() / ri.getQuantity())
                .min()
                .orElse(0);

    }

    public Double getAvailableQuantity() {
        return getAvailableQuantity(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishRest dishRest = (DishRest) o;
        return unitPrice == dishRest.unitPrice && Objects.equals(idDish, dishRest.idDish) && Objects.equals(name, dishRest.name) && Objects.equals(requiredIngredient, dishRest.requiredIngredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDish, name, unitPrice, requiredIngredient);
    }
}
