package edu.hei.school.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@JsonPropertyOrder({"id", "price", "dateValue"})
public class PriceIngredientDTO {
    private Long id;
    @JsonProperty("dateValue")
    private LocalDateTime effectiveDate;
    private Double price;

    @JsonCreator
    public PriceIngredientDTO(Long id, LocalDate effectiveDate, Double price) {
        this.id = id;
        this.effectiveDate = effectiveDate.atStartOfDay();
        this.price = price;
    }

    public PriceIngredientDTO(LocalDateTime effectiveDate, Double price) {
        this.effectiveDate = effectiveDate;
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceIngredientDTO that = (PriceIngredientDTO) o;
        return Objects.equals(effectiveDate, that.effectiveDate) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(effectiveDate, price);
    }

    @Override
    public String toString() {
        return "PriceIngredientDTO{" +
                "effectiveDate=" + effectiveDate +
                ", price=" + price +
                '}';
    }
}
