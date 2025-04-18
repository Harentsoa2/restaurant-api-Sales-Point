package edu.hei.school.restaurant.dto;

import edu.hei.school.restaurant.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ChangeStatus {
    private OrderStatus status;

    public ChangeStatus(OrderStatus status) {
        this.status = status;
    }

}
