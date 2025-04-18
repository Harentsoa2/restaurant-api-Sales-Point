package edu.hei.school.restaurant.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


@Setter
@Getter
@JsonPropertyOrder({"price", "stockMovements", "availableQuantity", "actualPrice", "id", "name"})
public class Ingredient {
    @JsonProperty("id")
    private Long idIngredient;
    @JsonProperty("name")
    private String nameIngredient;
    private Unity unity;
    @JsonProperty("prices")
    private List<PriceIngredient> priceIngredients;
    @JsonProperty("stockMovements")
    private List<StockMovement> stockMovements;

    public Ingredient(Long idIngredient, String nameIngredient, Unity unity, List<PriceIngredient> priceIngredients) {
        this.idIngredient = idIngredient;
        this.nameIngredient = nameIngredient;
        this.unity = unity;
        this.priceIngredients = priceIngredients;
    }


    public Ingredient(Long idIngredient, String nameIngredient, Unity unity, List<PriceIngredient> priceIngredients, List<StockMovement> stockMovements) {
        this.idIngredient = idIngredient;
        this.nameIngredient = nameIngredient;
        this.unity = unity;
        this.priceIngredients = priceIngredients;
        this.stockMovements = stockMovements;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "idIngredient=" + idIngredient +
                ", nameIngredient='" + nameIngredient + '\'' +
                ", unity=" + unity +
                ", priceIngredients=" + priceIngredients +
                ", stockMovements=" + stockMovements +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(idIngredient, that.idIngredient) && Objects.equals(nameIngredient, that.nameIngredient) && unity == that.unity && Objects.equals(priceIngredients, that.priceIngredients) && Objects.equals(stockMovements, that.stockMovements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIngredient, nameIngredient, unity, priceIngredients, stockMovements);
    }

    public Ingredient() {
    }

    public Ingredient(Long idIngredient, String nameIngredient, Unity unity) {
        this.idIngredient = idIngredient;
        this.nameIngredient = nameIngredient;
        this.unity = unity;
    }

    @JsonIgnore
    public Double getLatestPriceAtDate(LocalDateTime date) {
        return priceIngredients.stream()
                .filter(price -> !price.getEffectiveDate().isAfter(date))
                .max(Comparator.comparing(PriceIngredient::getEffectiveDate))
                .map(PriceIngredient::getPrice)
                .orElse(0.0);
    }

    @JsonProperty("actualPrice")
    public Double getActualPrice() {
        return getLatestPriceAtDate(LocalDateTime.now());
    }

    @JsonIgnore
    public LocalDateTime getLatestDate(LocalDateTime date) {
        return priceIngredients.stream()
                .filter(price -> !price.getEffectiveDate().isAfter(date))
                .max(Comparator.comparing(PriceIngredient::getEffectiveDate))
                .map(PriceIngredient::getEffectiveDate)
                .orElse(null);
    }

    @JsonIgnore
    public LocalDateTime getLatestDate() {
        return getLatestDate(LocalDateTime.now());
    }

    @JsonIgnore
    public BigDecimal getAvailableQuantity(LocalDateTime date) {
        if (stockMovements == null || stockMovements.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return stockMovements.stream()
                .filter(movement -> !movement.getMovementDate().isAfter(date))
                .map(movement -> movement.getMovementType() == MovementType.IN
                        ? movement.getQuantity()
                        : movement.getQuantity().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .max(BigDecimal.ZERO);
    }

    @JsonProperty("availableQuantity")
    public BigDecimal getAvailableQuantity() {
        return getAvailableQuantity(LocalDateTime.now());
    }

}
