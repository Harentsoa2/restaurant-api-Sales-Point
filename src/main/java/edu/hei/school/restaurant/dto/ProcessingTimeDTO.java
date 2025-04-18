package edu.hei.school.restaurant.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class ProcessingTimeDTO {
    private Long dishId;
    private String aggregation;
    private String unit;
    private Long value;

    public ProcessingTimeDTO(Long dishId, String aggregation, String unit, Long value) {
        this.dishId = dishId;
        this.aggregation = aggregation;
        this.unit = unit;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessingTimeDTO that = (ProcessingTimeDTO) o;
        return Objects.equals(dishId, that.dishId) && Objects.equals(aggregation, that.aggregation) && Objects.equals(unit, that.unit) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishId, aggregation, unit, value);
    }

    @Override
    public String toString() {
        return "ProcessingTimeDTO{" +
                "dishId=" + dishId +
                ", aggregation='" + aggregation + '\'' +
                ", unit='" + unit + '\'' +
                ", value=" + value +
                '}';
    }

}
