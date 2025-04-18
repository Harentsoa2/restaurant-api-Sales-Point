package edu.hei.school.restaurant.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class DishOrder {
    private Long id;
    private Dish dish;
    private int quantity;
    private List<StatusHistory> dishStatus;

    public DishOrder(Dish dish, int quantity, List<StatusHistory> dishStatus) {
        this.dish = dish;
        this.quantity = quantity;
        this.dishStatus = dishStatus;
    }

    public DishOrder(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }
    public Double getTotalPrice(){
        return (double) (dish.getUnitPrice() * quantity);
    }

    public DishOrder(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public DishOrder() {
    }


    public OrderStatus getActualStatus() {
        if(dishStatus == null){return OrderStatus.CREATED; }
        return dishStatus.stream().max(Comparator.comparing(StatusHistory::getStatusChangeDate))
                .map(StatusHistory::getStatus).orElse(null);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishOrder dishOrder = (DishOrder) o;
        return quantity == dishOrder.quantity && Objects.equals(id, dishOrder.id) && Objects.equals(dish, dishOrder.dish) && Objects.equals(dishStatus, dishOrder.dishStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dish, quantity, dishStatus);
    }

    @Override
    public String toString() {
        return "DishOrder{" +
                "id=" + id +
                ", dish=" + dish +
                ", quantity=" + quantity +
                ", dishStatus=" + dishStatus +
                '}';
    }
}
