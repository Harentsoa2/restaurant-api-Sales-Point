package edu.hei.school.restaurant.repository.dao.mapper;

import edu.hei.school.restaurant.entity.Unity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UnitMapper {

    public Unity mapFromResultSet(String stringValue) {
        if (stringValue == null) {
            return null;
        }
        List<Unity> unityList = Arrays.stream(Unity.values()).toList();
        return unityList.stream().filter(
                        unit -> stringValue.equals(unit.toString())
                ).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Unknown sex value " + stringValue));
    }
}
