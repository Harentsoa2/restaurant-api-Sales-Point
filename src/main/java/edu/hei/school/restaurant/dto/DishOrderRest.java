package edu.hei.school.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.hei.school.restaurant.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DishOrderRest {
    @JsonProperty("id")
    private Long id;
    @JsonIgnore
    private Double actualPrice;
    @JsonProperty("name")
    private String dishName;
    @JsonProperty("quantityOrdered")
    private Integer dishQuantity;
    @JsonProperty("actualOrderStatus")
    private OrderStatus orderStatus;

    public DishOrderRest(Long id, Double actualPrice, String dishName, Integer dishQuantity, OrderStatus orderStatus) {
        this.id = id;
        this.actualPrice = actualPrice;
        this.dishName = dishName;
        this.dishQuantity = dishQuantity;
        this.orderStatus = orderStatus;
    }

}
