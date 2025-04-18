package edu.hei.school.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.hei.school.restaurant.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderQuantityDish {
    @JsonProperty("orderStatus")
    private OrderStatus orderStatus;
    @JsonProperty("dishes")
    private List<QuantityDish> quantityDishes;

    public OrderQuantityDish(OrderStatus orderStatus, List<QuantityDish> quantityDishes) {
        this.orderStatus = orderStatus;
        this.quantityDishes = quantityDishes;
    }

}
