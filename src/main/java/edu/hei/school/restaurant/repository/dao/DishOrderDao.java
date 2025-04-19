package edu.hei.school.restaurant.repository.dao;

import edu.hei.school.restaurant.entity.DishOrder;
import edu.hei.school.restaurant.repository.DataSource;
import edu.hei.school.restaurant.repository.dao.mapper.OrderStatusMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishOrderDao {
    private DataSource dataSource;
    private OrderStatusMapper orderStatusMapper;
    private DishDao dishDao;
    private DishOrderHistoryDao statusHistoryDao;

    public DishOrderDao(DataSource dataSource, OrderStatusMapper orderStatusMapper, DishDao dishDao, DishOrderHistoryDao statusHistoryDao) {
        this.dataSource = dataSource;
        this.orderStatusMapper = orderStatusMapper;
        this.dishDao = dishDao;
        this.statusHistoryDao = statusHistoryDao;
    }

    public List<DishOrder> getDishOrderAll() {
        String dishOrderQuery = "SELECT id_dish_order, id_dish, quantity FROM DishOrder ";
        List<DishOrder> dishOrders = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement dishOrderStmt = connection.prepareStatement(dishOrderQuery)) {
            try (ResultSet dishOrderRs = dishOrderStmt.executeQuery()) {
                while (dishOrderRs.next()) {
                    DishOrder dishOrder = new DishOrder();
                    dishOrder.setId(dishOrderRs.getLong("id_dish_order"));
                    dishOrder.setDish(dishDao.getById(dishOrderRs.getLong("id_dish")));
                    dishOrder.setDishStatus(statusHistoryDao.getAllByOrderId(dishOrder.getId()));
                    dishOrder.setQuantity(dishOrderRs.getInt("quantity"));
                    dishOrders.add(dishOrder);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishOrders;
    }

    public List<DishOrder> getDishOrderByOrderId(String reference) {
        String dishOrderQuery = "SELECT id_dish_order, id_dish, quantity FROM DishOrder WHERE id_order = ?";
        List<DishOrder> dishOrders = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement dishOrderStmt = connection.prepareStatement(dishOrderQuery)) {
            dishOrderStmt.setString(1, reference);
            try (ResultSet dishOrderRs = dishOrderStmt.executeQuery()) {
                while (dishOrderRs.next()) {
                    DishOrder dishOrder = new DishOrder();
                    dishOrder.setId(dishOrderRs.getLong("id_dish_order"));
                    dishOrder.setDish(dishDao.getById(dishOrderRs.getLong("id_dish")));
                    dishOrder.setDishStatus(statusHistoryDao.getAllByOrderId(dishOrder.getId()));
                    dishOrder.setQuantity(dishOrderRs.getInt("quantity"));
                    dishOrders.add(dishOrder);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishOrders;
    }

    public List<DishOrder> saveAll(String reference, List<DishOrder> dishOrders) {
        for(DishOrder dishOrder : dishOrders) {
            saveOne(reference, dishOrder);
        }
        return dishOrders;
    }

    public DishOrder saveOne(String reference, DishOrder dishOrder) {
        String sql = "insert into dishorder (id_order, id_dish, quantity) values (?, ?, ?)"+
                        "on conflict (id_order, id_dish) do update set quantity = excluded.quantity";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
        ){
            pstmt.setString(1, reference);
            pstmt.setLong(2, dishOrder.getDish().getIdDish());
            pstmt.setInt(3, dishOrder.getQuantity());
            pstmt.executeUpdate();


        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishOrder;
    }




}
