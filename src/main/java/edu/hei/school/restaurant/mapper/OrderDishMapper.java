package edu.hei.school.restaurant.mapper;

import edu.hei.school.restaurant.dto.QuantityDish;
import edu.hei.school.restaurant.entity.DishOrder;
import edu.hei.school.restaurant.repository.dao.DishDao;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class OrderDishMapper {
    private DishDao dishDao;

    public OrderDishMapper(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    public List<DishOrder> toModel(List<QuantityDish> quantityDishes) {
        List<DishOrder> dishOrders = quantityDishes.stream().map(i ->
                new DishOrder(
                        dishDao.getById(i.getIdDish()),
                        Math.toIntExact(i.getQuantity())
                )
                ).toList();
        System.out.println(dishOrders);
        return dishOrders;
    }
}
