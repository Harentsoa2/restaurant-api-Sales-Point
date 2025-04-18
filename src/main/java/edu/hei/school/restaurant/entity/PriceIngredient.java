package edu.hei.school.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;


@Setter
@Getter
@JsonPropertyOrder({"id", "price", "dateValue"})
public class PriceIngredient {
    @JsonProperty("id")
    private Long id;
    private Ingredient ingredient;
    @JsonProperty("dateValue")
    private LocalDateTime effectiveDate;
    private Double price;

    public PriceIngredient(Long id, Ingredient ingredient, LocalDateTime effectiveDate, Double price) {
        this.id = id;
        this.ingredient = ingredient;
        this.effectiveDate = effectiveDate;
        this.price = price;
    }

    public PriceIngredient(Long id, LocalDateTime effectiveDate, Double price) {
        this.id = id;
        this.effectiveDate = effectiveDate;
        this.price = price;
    }

    public PriceIngredient() {
    }

    @JsonIgnore
    public Ingredient getIngredient() {
        return ingredient;
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceIngredient that = (PriceIngredient) o;
        return Objects.equals(id, that.id) && Objects.equals(ingredient, that.ingredient) && Objects.equals(effectiveDate, that.effectiveDate) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ingredient, effectiveDate, price);
    }

    @Override
    public String toString() {
        return "PriceIngredient{" +
                "id=" + id +
                ", ingredient=" + ingredient +
                ", effectiveDate=" + effectiveDate +
                ", price=" + price +
                '}';
    }
}
