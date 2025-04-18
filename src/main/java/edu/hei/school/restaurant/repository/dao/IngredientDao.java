package edu.hei.school.restaurant.repository.dao;

import edu.hei.school.restaurant.entity.RequiredIngredient;
import edu.hei.school.restaurant.repository.DataSource;
import edu.hei.school.restaurant.repository.dao.mapper.UnitMapper;
import edu.hei.school.restaurant.entity.Ingredient;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class IngredientDao {
    private DataSource dataSource;
    private UnitMapper unitMapper;
    private PriceDao priceDao;
    private StockMovementDao stockMovementDao;


    public IngredientDao(DataSource dataSource, PriceDao priceDao, StockMovementDao stockMovementDao, UnitMapper unitMapper) {
        this.dataSource = dataSource;
        this.priceDao = priceDao;
        this.stockMovementDao = stockMovementDao;
        this.unitMapper = unitMapper;
    }

    public List<RequiredIngredient> getIngredientByDishId(Long idDish){
        String sql = "select id_ingredient, id_dish, required_quantity from dish_ingredient where id_dish = ?";
        List<RequiredIngredient> requiredIngredients = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        try(
            PreparedStatement pstmp = connection.prepareStatement(sql)){
            pstmp.setLong(1, idDish);
            try(ResultSet rs = pstmp.executeQuery()){
                while(rs.next()){
                    RequiredIngredient requiredIngredient = new RequiredIngredient();
                    requiredIngredient.setIngredient(this.getById(rs.getLong("id_ingredient")));
                    requiredIngredient.setQuantity(rs.getDouble("required_quantity"));
                    requiredIngredients.add(requiredIngredient);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return requiredIngredients;
    }


    public List<Ingredient> getAll(Optional<Double> minPrice, Optional<Double> maxPrice) {
        System.out.println("je suis la");
        StringBuilder sql = new StringBuilder(
                "SELECT i.id_ingredient, name, unit FROM ingredient i " +
                        "Left JOIN price p ON i.id_ingredient = p.id_ingredient "

        );

        if (minPrice.isPresent() || maxPrice.isPresent()) {
            sql.append(" WHERE p.effective_date = (SELECT MAX(p2.effective_date) FROM price p2 WHERE p2.id_ingredient = p.id_ingredient) AND p.unit_price BETWEEN ? AND ?");
        }

        List<Ingredient> ingredients = new ArrayList<>();

        try (
                Connection con = dataSource.getConnection();
                PreparedStatement pstmp = con.prepareStatement(sql.toString())
        ) {
            int index = 1;
            if (minPrice.isPresent() || maxPrice.isPresent()) {
                pstmp.setDouble(index++, minPrice.orElse(0d));
                pstmp.setDouble(index++, maxPrice.orElse(Double.MAX_VALUE));
            }

            try (ResultSet rs = pstmp.executeQuery()) {
                while (rs.next()) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setIdIngredient(rs.getLong("id_ingredient"));
                    ingredient.setUnity(unitMapper.mapFromResultSet(rs.getString("unit")));
                    ingredient.setNameIngredient(rs.getString("name"));
                    ingredient.setPriceIngredients(priceDao.getByIngredient(rs.getLong("id_ingredient")));
                    ingredient.setStockMovements(stockMovementDao.getByIngredient(rs.getLong("id_ingredient")));
                    ingredients.add(ingredient);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ingredients;
    }


    public Ingredient getById(Long id) {
        String sql = "Select id_ingredient, name, unit from ingredient i where id_ingredient = ?";
        Ingredient ingredient = null;
        Connection connection = dataSource.getConnection();
        try(
                PreparedStatement pstmpt = connection.prepareStatement(sql);
                ) {
                        pstmpt.setLong(1, id);
                        ResultSet rs = pstmpt.executeQuery();
                        if(rs.next()){
                            ingredient = new Ingredient(rs.getLong("id_ingredient"), rs.getString("name"), unitMapper.mapFromResultSet(rs.getString("unit")));
                            ingredient.setPriceIngredients(priceDao.getByIngredient(rs.getLong("id_ingredient")));
                            ingredient.setStockMovements(stockMovementDao.getByIngredient(rs.getLong("id_ingredient")));
                        }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ingredient;
    }

    public List<Ingredient> saveAll(List<Ingredient> ingredients){
        for(Ingredient ingredient : ingredients){
            saveOne(ingredient);
        }
        return ingredients;
    }

    public Ingredient saveOne(Ingredient ingredient) {
        String sql = "INSERT INTO ingredient (id_ingredient, name, unit) VALUES (?, ?, ?)"+
                     "ON CONFLICT (id_ingredient) DO UPDATE SET name = excluded.name, unit = excluded.unit";


        try(Connection connection = dataSource.getConnection()) {
            try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setLong(1, ingredient.getIdIngredient());
                pstmt.setString(2, ingredient.getNameIngredient());
                pstmt.setObject(3, ingredient.getUnity().name(), Types.OTHER);
                priceDao.saveAll(ingredient.getPriceIngredients(), ingredient.getIdIngredient());
                stockMovementDao.saveAll(ingredient.getStockMovements(), ingredient.getIdIngredient());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ingredient;

    }

    public List<RequiredIngredient> addAllIngredient(Long idDish, List<RequiredIngredient> requiredIngredients){
        for(RequiredIngredient requiredIngredient : requiredIngredients){
            addIngredient(idDish, requiredIngredient);
        }
        return requiredIngredients;
    }


    public RequiredIngredient addIngredient(Long idDish, RequiredIngredient requiredIngredient) {
        saveOne(requiredIngredient.getIngredient());
        String sql = "insert into dish_ingredient (id_dish, id_ingredient, required_quantity, unit) VALUES (?, ?, ?, ?)"+
                        "on conflict (id_ingredient, id_dish) DO UPDATE SET required_quantity = excluded.required_quantity";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement pstmp = connection.prepareStatement(sql);
        ){
                pstmp.setLong(1, idDish);
                pstmp.setLong(2, requiredIngredient.getIngredient().getIdIngredient());
                pstmp.setDouble(3, requiredIngredient.getQuantity());
                pstmp.setObject(4, requiredIngredient.getIngredient().getUnity().name(), Types.OTHER);
                pstmp.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return requiredIngredient;
    }




}
