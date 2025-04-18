package edu.hei.school.restaurant.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
public class StatusHistory {
    private LocalDateTime statusChangeDate;
    private OrderStatus status;


    public StatusHistory(LocalDateTime statusChangeDate, OrderStatus status) {
        this.statusChangeDate = statusChangeDate;
        this.status = status;
    }

    public StatusHistory() {
    }

    @Override
    public String toString() {
        return "StatusHistory{" +
                "statusChangeDate=" + statusChangeDate +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusHistory that = (StatusHistory) o;
        return Objects.equals(statusChangeDate, that.statusChangeDate) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusChangeDate, status);
    }
}
