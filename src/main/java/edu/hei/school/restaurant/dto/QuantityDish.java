package edu.hei.school.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuantityDish {
    @JsonProperty("dishIdentifier")
    private Long idDish;
    @JsonProperty("quantityOrdered")
    private Long quantity;

    public QuantityDish(Long idDish, Long quantity) {
        this.idDish = idDish;
        this.quantity = quantity;
    }

}
