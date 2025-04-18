package edu.hei.school.restaurant.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class Order {
    private Long reference;
    private LocalDateTime orderDate;
    private List<StatusHistory> orderStatusHistories;
    private List<DishOrder> dishOrders;

    public Order(Long reference, LocalDateTime orderDate, List<DishOrder> dishOrders) {
        this.reference =  reference;
        this.orderDate = orderDate;
        this.orderStatusHistories = List.of(new StatusHistory(orderDate, OrderStatus.CREATED));
        this.dishOrders = dishOrders;
    }

    public Order(Long reference, LocalDateTime orderDate) {
        this.reference = reference;
        this.orderDate = orderDate;
        this.orderStatusHistories = List.of(new StatusHistory(orderDate, OrderStatus.CREATED));;
    }

    public Order() {

    }

    public OrderStatus getActualStatus() {
        return orderStatusHistories.stream().max(Comparator.comparing(StatusHistory::getStatusChangeDate))
                .map(StatusHistory::getStatus).orElse(null);
    }

    public double getTotalAmount() {
        if (dishOrders == null || dishOrders.isEmpty()) {
            return 0.0;
        }
        return dishOrders.stream()
                .mapToDouble(DishOrder::getTotalPrice)
                .sum();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(reference, order.reference) && Objects.equals(orderDate, order.orderDate) && Objects.equals(orderStatusHistories, order.orderStatusHistories) && Objects.equals(dishOrders, order.dishOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, orderDate, orderStatusHistories, dishOrders);
    }

    @Override
    public String toString() {
        return "Order{" +
                "reference=" + reference +
                ", orderDate=" + orderDate +
                ", orderStatusHistories=" + orderStatusHistories +
                ", dishOrders=" + dishOrders +
                '}';
    }
}
