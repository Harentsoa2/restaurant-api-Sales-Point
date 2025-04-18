package edu.hei.school.restaurant.controller;

import edu.hei.school.restaurant.Service.DishService;
import edu.hei.school.restaurant.dto.DishRest;
import edu.hei.school.restaurant.dto.IngredientWithQuantityRest;
import edu.hei.school.restaurant.entity.RequiredIngredient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DishRestController {
    private DishService dishService;

    public DishRestController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/dishes")
    public ResponseEntity<Object> getDishes() {
       List<DishRest> dishes =  dishService.getAll();
        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }

    @PutMapping("/dishes/{id}/ingredients")
    public ResponseEntity<Object> updateDishIngredients(@PathVariable Long id, @RequestBody List<IngredientWithQuantityRest> ingredients) {
        DishRest dishRest = dishService.addAllIngredient(id, ingredients);
        return new ResponseEntity<>(dishRest, HttpStatus.OK);
    }

}
