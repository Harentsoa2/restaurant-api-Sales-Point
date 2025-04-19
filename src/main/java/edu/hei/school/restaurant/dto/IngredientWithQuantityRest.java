package edu.hei.school.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.hei.school.restaurant.entity.Unity;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@JsonPropertyOrder({"requiredQuantity", "id", "name", "unit"})
public class IngredientWithQuantityRest {
    @JsonProperty("id")
    private Long idIngredient;
    @JsonProperty("name")
    private String nameIngredient;
    @JsonProperty("unit")
    private Unity unity;
    @JsonProperty("requiredQuantity")
    private Double quantity;

    @JsonCreator
    public IngredientWithQuantityRest(Long idIngredient, String nameIngredient, Unity unity, Double quantity) {
        this.idIngredient = idIngredient;
        this.nameIngredient = nameIngredient;
        this.unity = unity;
        this.quantity = quantity;
    }


    public IngredientWithQuantityRest(Long idIngredient, Double quantity, String nameIngredient) {
        this.idIngredient = idIngredient;
        this.quantity = quantity;
        this.nameIngredient = nameIngredient;
    }

}
