package edu.hei.school.restaurant.controller;

import edu.hei.school.restaurant.Service.IngredientService;
import edu.hei.school.restaurant.dto.PriceIngredientDTO;
import edu.hei.school.restaurant.dto.StockMovementDTO;
import edu.hei.school.restaurant.entity.Ingredient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class IngredientRestController {
    private final IngredientService ingredientService;

    public IngredientRestController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredients")
    public ResponseEntity<Object> getIngredients(@RequestParam(name = "priceMinFilter", required = false) Double priceMinFilter,
                                                 @RequestParam(name = "priceMaxFilter", required = false) Double priceMaxFilter) {
        if (priceMinFilter != null && priceMinFilter < 0) {
            return new ResponseEntity<>("PriceMinFilter " + priceMinFilter + " is negative", HttpStatus.BAD_REQUEST);
        }
        if (priceMaxFilter != null && priceMaxFilter < 0) {
            return new ResponseEntity<>("PriceMaxFilter " + priceMaxFilter + " is negative", HttpStatus.BAD_REQUEST);
        }
        if (priceMinFilter != null && priceMaxFilter != null) {
            if (priceMinFilter > priceMaxFilter) {
                return new ResponseEntity<>("PriceMinFilter " + priceMinFilter + " is greater than PriceMaxFilter " + priceMaxFilter, HttpStatus.BAD_REQUEST);
            }
        }

        Optional<Double> minPrice = Optional.ofNullable(priceMinFilter);
        Optional<Double> maxPrice = Optional.ofNullable(priceMaxFilter);

        List<Ingredient> filteredIngredients = ingredientService.getAll(minPrice, maxPrice);

        return new ResponseEntity<>(filteredIngredients, HttpStatus.OK);
    }


    @PutMapping("/ingredients")
    public ResponseEntity<Object> updateIngredients(@RequestBody List<Ingredient> ingredients) {
        return new ResponseEntity<>(ingredientService.saveAll(ingredients), HttpStatus.OK);
    }

    @PutMapping("/ingredients/{idIngredient}/prices")
    public ResponseEntity<Object> addPrice(@PathVariable Long idIngredient, @RequestBody List<PriceIngredientDTO> priceIngredientDTOs) {
        return new ResponseEntity<>(ingredientService.addPrice(idIngredient, priceIngredientDTOs), HttpStatus.OK);
    }

    @PutMapping("/ingredients/{idIngredient}/stockMovement")
    public ResponseEntity<Object> addStockMovement(@PathVariable Long idIngredient, @RequestBody List<StockMovementDTO> stockMovementDTOs) {
        return new ResponseEntity<>(ingredientService.addStockMovement(idIngredient, stockMovementDTOs), HttpStatus.OK);
    }


    @GetMapping("/ingredients/{id}")
    public ResponseEntity<Object> getIngredient(@PathVariable Long id) {
        Optional<Ingredient> optionalIngredient = Optional.ofNullable(ingredientService.getById(id));
        if (optionalIngredient.isPresent()) {
            return new ResponseEntity<>(optionalIngredient.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("id = "+ id + "not found" ,HttpStatus.NOT_FOUND);
    }





}
