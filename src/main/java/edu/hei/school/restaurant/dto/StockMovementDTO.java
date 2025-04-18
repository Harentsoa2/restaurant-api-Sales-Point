package edu.hei.school.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.hei.school.restaurant.entity.MovementType;
import edu.hei.school.restaurant.entity.Unity;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@JsonPropertyOrder({"id", "quantity", "unit", "type", "creationDatetime"})
public class StockMovementDTO {
    private Long id;
    @JsonProperty("type")
    private MovementType movementType; // "ENTREE" ou "SORTIE"
    private BigDecimal quantity;
    @JsonProperty("unit")
    private Unity unity;
    @JsonProperty("creationDatetime")
    private LocalDateTime movementDate;

    public StockMovementDTO(MovementType movementType, BigDecimal quantity, Unity unity, LocalDateTime movementDate) {
        this.movementType = movementType;
        this.quantity = quantity;
        this.unity = unity;
        this.movementDate = movementDate;
    }

    @JsonCreator
    public StockMovementDTO(Long id, MovementType movementType, BigDecimal quantity, Unity unity) {
        this.id = id;
        this.movementType = movementType;
        this.quantity = quantity;
        this.unity = unity;
    }

    public StockMovementDTO() {
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockMovementDTO that = (StockMovementDTO) o;
        return movementType == that.movementType && Objects.equals(quantity, that.quantity) && unity == that.unity && Objects.equals(movementDate, that.movementDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movementType, quantity, unity, movementDate);
    }

    @Override
    public String toString() {
        return "StockMovementDTO{" +
                "movementType=" + movementType +
                ", quantity=" + quantity +
                ", unity=" + unity +
                ", movementDate=" + movementDate +
                '}';
    }
}
