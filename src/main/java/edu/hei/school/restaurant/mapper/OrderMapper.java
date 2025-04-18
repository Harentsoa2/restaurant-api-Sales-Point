package edu.hei.school.restaurant.mapper;

import edu.hei.school.restaurant.dto.DishOrderRest;
import edu.hei.school.restaurant.dto.OrderRest;
import edu.hei.school.restaurant.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderRest toRest(Order order) {
        if(order.getReference() == null) {
            return null;
        }else {
            List<DishOrderRest> dishOrderRestList = order.getDishOrders().stream().map(i ->
                    new DishOrderRest(
                            i.getDish().getIdDish(),
                            (double) i.getDish().getUnitPrice(),
                            i.getDish().getName(),
                            i.getQuantity(),
                            i.getActualStatus()
                    )
            ).toList();


            return new OrderRest(
                    order.getReference(),
                    dishOrderRestList,
                    order.getActualStatus(),
                    order.getTotalAmount()
            );
        }
    }
}
