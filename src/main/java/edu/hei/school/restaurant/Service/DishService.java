package edu.hei.school.restaurant.Service;

import edu.hei.school.restaurant.dto.DishCreation;
import edu.hei.school.restaurant.dto.DishRest;
import edu.hei.school.restaurant.dto.IngredientWithQuantityRest;
import edu.hei.school.restaurant.entity.Dish;
import edu.hei.school.restaurant.entity.RequiredIngredient;
import edu.hei.school.restaurant.mapper.DIshMapper;
import edu.hei.school.restaurant.mapper.IngredientWithQuantityMapper;
import edu.hei.school.restaurant.repository.dao.DishDao;
import edu.hei.school.restaurant.repository.dao.IngredientDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    private DishDao dishDao;
    private IngredientDao ingredientDao;
    private IngredientWithQuantityMapper ingredientWithQuantityMapper;
    private DIshMapper dishMapper;

    public DishService(DishDao dishDao, IngredientDao ingredientDao, IngredientWithQuantityMapper ingredientWithQuantityMapper, DIshMapper dishMapper) {
        this.dishDao = dishDao;
        this.ingredientDao = ingredientDao;
        this.ingredientWithQuantityMapper = ingredientWithQuantityMapper;
        this.dishMapper = dishMapper;
    }

    public List<DishRest> getAll(){
        List<Dish> dishes = dishDao.getAll();
        return dishes.stream().map(i -> dishMapper.toRest(i)).toList();
    }

    public DishRest addAllIngredient(Long idDish, List<IngredientWithQuantityRest> ingredientWithQuantityRests) {
        List<RequiredIngredient> requiredIngredients = ingredientWithQuantityRests.stream().map(i -> ingredientWithQuantityMapper.toModel(i)).toList();
       ingredientDao.addAllIngredient(idDish, requiredIngredients);
        return dishMapper.toRest(dishDao.getById(idDish));
    }

    public List<DishRest> saveAll(List<DishCreation> dishes){
        dishDao.saveAll(dishes);
        return getAll();
    }



}
