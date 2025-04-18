package edu.hei.school.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@JsonPropertyOrder({"id", "quantity","unit", "type", "creationDateTime"})
public class StockMovement {
    private Long id;
    @JsonIgnore
    private Ingredient ingredient;
    @JsonProperty("type")
    private MovementType movementType; // "ENTREE" ou "SORTIE"
    private BigDecimal quantity;
    @JsonProperty("unit")
    private Unity unity;
    @JsonProperty("creationDateTime")
    private LocalDateTime movementDate;

    public StockMovement(Long id, Ingredient ingredient, MovementType movementType, BigDecimal quantity, Unity unity, LocalDateTime movementDate) {
        this.id = id;
        this.ingredient = ingredient;
        this.movementType = movementType;
        this.quantity = quantity;
        this.unity = unity;
        this.movementDate = movementDate;
    }

    public StockMovement(MovementType movementType, BigDecimal quantity, Unity unity, LocalDateTime movementDate) {
        this.movementType = movementType;
        this.quantity = quantity;
        this.unity = unity;
        this.movementDate = movementDate;
    }

    public StockMovement() {
    }

    @JsonIgnore
    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockMovement that = (StockMovement) o;
        return Objects.equals(id, that.id) && Objects.equals(ingredient, that.ingredient) && movementType == that.movementType && Objects.equals(quantity, that.quantity) && unity == that.unity && Objects.equals(movementDate, that.movementDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ingredient, movementType, quantity, unity, movementDate);
    }

    @Override
    public String toString() {
        return "StockMovement{" +
                "id=" + id +
                ", ingredient=" + ingredient +
                ", movementType=" + movementType +
                ", quantity=" + quantity +
                ", unity=" + unity +
                ", movementDate=" + movementDate +
                '}';
    }
}
