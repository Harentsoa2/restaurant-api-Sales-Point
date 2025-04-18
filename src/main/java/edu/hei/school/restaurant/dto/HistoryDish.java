package edu.hei.school.restaurant.dto;

import edu.hei.school.restaurant.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
public class HistoryDish {
    private LocalDateTime statusChangeDate;
    private OrderStatus status;
    private Long idDishOrder;
    private Long idDish;

    public HistoryDish(LocalDateTime statusChangeDate, OrderStatus status, Long idDishOrder, Long idDish) {
        this.statusChangeDate = statusChangeDate;
        this.status = status;
        this.idDishOrder = idDishOrder;
        this.idDish = idDish;
    }

    public HistoryDish() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryDish that = (HistoryDish) o;
        return Objects.equals(statusChangeDate, that.statusChangeDate) && status == that.status && Objects.equals(idDishOrder, that.idDishOrder) && Objects.equals(idDish, that.idDish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusChangeDate, status, idDishOrder, idDish);
    }

}
