package edu.hei.school.restaurant.repository.dao.mapper;

import edu.hei.school.restaurant.entity.MovementType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MovementTypeMapper {
    public MovementType mapFromResultSet(String stringValue) {
        if (stringValue == null) {
            return null;
        }
        List<MovementType> movementTypeList = Arrays.stream(MovementType.values()).toList();
        return movementTypeList.stream().filter(
                        movementType -> stringValue.equals(movementType.toString())
                ).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Unknown movementType value " + stringValue));
    }
}
