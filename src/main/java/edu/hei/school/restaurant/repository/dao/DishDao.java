package edu.hei.school.restaurant.repository.dao;

import edu.hei.school.restaurant.entity.Dish;
import edu.hei.school.restaurant.repository.DataSource;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishDao {
    private DataSource dataSource;
    private IngredientDao ingredientDao;

    public DishDao(DataSource dataSource, IngredientDao ingredientDao) {
        this.dataSource = dataSource;
        this.ingredientDao = ingredientDao;
    }

    public List<Dish> getAll() {
        List<Dish> dishes = new ArrayList<>();
        String sql = "select id_dish, name, unit_price from dish";

        try (
                Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setIdDish(resultSet.getLong("id_dish"));
                dish.setName(resultSet.getString("name"));
                dish.setUnitPrice(resultSet.getInt("unit_price"));
                dish.setRequiredIngredients(ingredientDao.getIngredientByDishId(resultSet.getLong("id_dish")));
                dishes.add(dish);
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return dishes;
    }

    public Dish getById(Long id) {
        Dish dish = null;
        String sql = "select id_dish, name, unit_price from dish where id_dish = ? ";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            )
        {
            preparedStatement.setLong(1, id);
            try ( ResultSet rs = preparedStatement.executeQuery();){

                if (rs.next()) {
                    dish = new Dish();
                    dish.setIdDish(id);
                    dish.setName(rs.getString("name"));
                    dish.setUnitPrice(rs.getInt("unit_price"));
                    dish.setRequiredIngredients(ingredientDao.getIngredientByDishId(id));
                }
            }
        }catch (SQLException e ){
            throw new RuntimeException(e);
        }
        return dish;
    }







}
