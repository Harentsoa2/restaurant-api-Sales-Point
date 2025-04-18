package edu.hei.school.restaurant.repository.dao.mapper;


import edu.hei.school.restaurant.entity.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class OrderStatusMapper {
    public OrderStatus mapFromResultSet(String stringValue) {
        if (stringValue == null) {
            return null;
        }
        List<OrderStatus> orderStatusList = Arrays.stream(OrderStatus.values()).toList();
        return orderStatusList.stream().filter(
                        status -> stringValue.equals(status.toString())
                ).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Unknown orderStatus value " + stringValue));
    }
}
