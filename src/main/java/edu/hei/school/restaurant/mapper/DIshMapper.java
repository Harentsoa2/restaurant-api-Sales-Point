package edu.hei.school.restaurant.mapper;

import edu.hei.school.restaurant.dto.DishRest;
import edu.hei.school.restaurant.dto.IngredientDishRest;
import edu.hei.school.restaurant.dto.RequiredIngredientRest;
import edu.hei.school.restaurant.entity.Dish;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DIshMapper {

    public DishRest toRest(Dish dish) {
        List<RequiredIngredientRest> requiredIngredientRests = dish.getRequiredIngredients().stream().map(i ->
                new RequiredIngredientRest(i.getQuantity(),
                        new IngredientDishRest(
                                i.getIngredient().getIdIngredient(),
                                i.getIngredient().getNameIngredient(),
                                i.getIngredient().getUnity(),
                                i.getIngredient().getActualPrice(),
                                i.getIngredient().getAvailableQuantity()
                        )
                )
        ).toList();

        return new DishRest(
                dish.getIdDish(),
                dish.getName(),
                dish.getUnitPrice(),
                requiredIngredientRests
        );
    }


}
