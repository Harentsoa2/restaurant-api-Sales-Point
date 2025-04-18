package edu.hei.school.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.hei.school.restaurant.entity.Unity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;


@Getter
@Setter
public class IngredientDishRest {
    @JsonProperty("id")
    private Long idIngredient;
    @JsonProperty("name")
    private String nameIngredient;
    @JsonIgnore
    private Unity unity;
    @JsonIgnore
    private Double actualPrice;
    @JsonIgnore
    private BigDecimal actualstock;

    public IngredientDishRest(Long idIngredient, String nameIngredient, Unity unity, Double actualPrice, BigDecimal actualstock) {
        this.idIngredient = idIngredient;
        this.nameIngredient = nameIngredient;
        this.unity = unity;
        this.actualPrice = actualPrice;
        this.actualstock = actualstock;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientDishRest that = (IngredientDishRest) o;
        return Objects.equals(idIngredient, that.idIngredient) && Objects.equals(nameIngredient, that.nameIngredient) && unity == that.unity && Objects.equals(actualPrice, that.actualPrice) && Objects.equals(actualstock, that.actualstock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIngredient, nameIngredient, unity, actualPrice, actualstock);
    }

    @Override
    public String toString() {
        return "IngredientRest{" +
                "idIngredient=" + idIngredient +
                ", nameIngredient='" + nameIngredient + '\'' +
                ", unity=" + unity +
                ", actualPrice=" + actualPrice +
                ", actualstock=" + actualstock +
                '}';
    }
}
