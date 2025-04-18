package edu.hei.school.restaurant.mapper;

import edu.hei.school.restaurant.dto.PriceIngredientDTO;
import edu.hei.school.restaurant.dto.StockMovementDTO;
import edu.hei.school.restaurant.entity.PriceIngredient;
import edu.hei.school.restaurant.entity.StockMovement;
import edu.hei.school.restaurant.repository.dao.IngredientDao;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class IngredientMapper {
    private final IngredientDao ingredientDao;

    public IngredientMapper(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public PriceIngredient toModel(Long idIngredient, PriceIngredientDTO priceIngredientDTO){
        return new PriceIngredient(
                priceIngredientDTO.getId(),
                ingredientDao.getById(idIngredient),
                priceIngredientDTO.getEffectiveDate(),
                priceIngredientDTO.getPrice()
        );
    }

    public PriceIngredientDTO toDTO(PriceIngredient priceIngredient){
        return new PriceIngredientDTO(
                priceIngredient.getEffectiveDate(),
                priceIngredient.getPrice()
        );
    }

    public StockMovement toModel(Long idIngredient, StockMovementDTO stockMovementDTO){
        return new StockMovement(
                stockMovementDTO.getId(),
                ingredientDao.getById(idIngredient),
                stockMovementDTO.getMovementType(),
                stockMovementDTO.getQuantity(),
                stockMovementDTO.getUnity(),
                stockMovementDTO.getMovementDate()
        );
    }

    public StockMovementDTO toDTO(StockMovement stockMovement){
        return new StockMovementDTO(
                stockMovement.getMovementType(),
                stockMovement.getQuantity(),
                stockMovement.getUnity(),
                stockMovement.getMovementDate()
        );
    }



}
