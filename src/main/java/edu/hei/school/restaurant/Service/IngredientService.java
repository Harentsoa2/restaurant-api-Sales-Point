package edu.hei.school.restaurant.Service;

import edu.hei.school.restaurant.dto.PriceIngredientDTO;
import edu.hei.school.restaurant.dto.StockMovementDTO;
import edu.hei.school.restaurant.entity.PriceIngredient;
import edu.hei.school.restaurant.entity.StockMovement;
import edu.hei.school.restaurant.mapper.IngredientMapper;
import edu.hei.school.restaurant.repository.dao.IngredientDao;
import edu.hei.school.restaurant.repository.dao.PriceDao;
import edu.hei.school.restaurant.entity.Ingredient;
import edu.hei.school.restaurant.repository.dao.StockMovementDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    private IngredientDao ingredientDao;
    private PriceDao priceDao;
    private IngredientMapper ingredientMapper;
    private StockMovementDao stockMovementDao;


    public IngredientService(IngredientDao ingredientDao, PriceDao priceDao, IngredientMapper ingredientMapper, StockMovementDao stockMovementDao) {
        this.ingredientDao = ingredientDao;
        this.priceDao = priceDao;
        this.ingredientMapper = ingredientMapper;
        this.stockMovementDao = stockMovementDao;
    }

    public List<Ingredient> getAll(Optional<Double> minPrice, Optional<Double> maxPrice) {
        return ingredientDao.getAll(minPrice, maxPrice);
    }

    public Ingredient getById(Long id) {
        return ingredientDao.getById(id);
    }

    public List<Ingredient> saveAll(List<Ingredient> ingredients) {
        return ingredientDao.saveAll(ingredients);
    }

    public  Ingredient  addPrice(Long idIngredient, List<PriceIngredientDTO> priceIngredientDTO) {
        List<PriceIngredient> priceIngredients = priceIngredientDTO.stream().map(priceDTO -> ingredientMapper.toModel(idIngredient, priceDTO)).toList();
         priceDao.saveAll(priceIngredients, idIngredient);
         return this.getById(idIngredient);
    }

    public Ingredient addStockMovement(Long idIngredient, List<StockMovementDTO> stockMovementDTO) {
        List<StockMovement>  stockMovements =   stockMovementDTO.stream().map(stockMovementDTO1 -> ingredientMapper.toModel(idIngredient, stockMovementDTO1)).toList();
       stockMovementDao.saveAll(stockMovements, idIngredient);
       return this.getById(idIngredient);
    }

}
