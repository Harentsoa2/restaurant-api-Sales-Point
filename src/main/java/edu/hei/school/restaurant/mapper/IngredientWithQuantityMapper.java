package edu.hei.school.restaurant.mapper;

import edu.hei.school.restaurant.dto.IngredientWithQuantityRest;
import edu.hei.school.restaurant.entity.RequiredIngredient;
import edu.hei.school.restaurant.repository.dao.IngredientDao;
import org.springframework.stereotype.Component;

@Component
public class IngredientWithQuantityMapper {
    private IngredientDao ingredientDao;

    public IngredientWithQuantityMapper(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public RequiredIngredient toModel(IngredientWithQuantityRest ingredientWithQuantityRest){
        return new RequiredIngredient(
                ingredientWithQuantityRest.getQuantity(),
                ingredientDao.getById(ingredientWithQuantityRest.getIdIngredient())
        );
    }


}
