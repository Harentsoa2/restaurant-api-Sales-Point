package edu.hei.school.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.hei.school.restaurant.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@JsonPropertyOrder({"id", "totalAmount", "actualStatus", "dishes"})
public class OrderRest {
    @JsonProperty("id")
    private Long reference;
    @JsonProperty("dishes")
    private List<DishOrderRest> dishOrderRests;
    @JsonProperty("actualStatus")
    private OrderStatus status;
    private Double totalAmount;



}
