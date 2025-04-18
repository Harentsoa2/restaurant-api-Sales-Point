package edu.hei.school.restaurant.repository.dao;

import edu.hei.school.restaurant.Service.OrderService;
import edu.hei.school.restaurant.dto.DishSales;
import edu.hei.school.restaurant.entity.Order;
import edu.hei.school.restaurant.entity.OrderStatus;
import edu.hei.school.restaurant.entity.StatusHistory;
import edu.hei.school.restaurant.repository.DataSource;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDao {
    private DataSource dataSource;
    private DishOrderDao dishOrderDao;
    private OrderHistoryDao orderHistoryDao;

    public OrderDao(DataSource dataSource, DishOrderDao dishOrderDao, OrderHistoryDao orderHistoryDao ){
        this.dataSource = dataSource;
        this.dishOrderDao = dishOrderDao;
        this.orderHistoryDao = orderHistoryDao;
    }

    public Order getById(Long reference) {
        Order order = new Order();
        String sql = "select id_order, created_at from \"Order\" where id_order = ?";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, reference);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    order.setReference(rs.getLong("id_order"));
                    order.setOrderDate(rs.getObject("created_at", LocalDateTime.class));
                    order.setDishOrders(dishOrderDao.getDishOrderByOrderId(rs.getLong("id_order")));
                    order.setOrderStatusHistories(orderHistoryDao.getAllByOrderId(rs.getLong("id_order")));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    public Order insertOrder(Long idOrder) {
        String sql = "INSERT INTO \"Order\" (id_order, created_at) VALUES (?, ?) on conflict (id_order) do update set created_at = excluded.created_at";
        LocalDateTime createdAt = LocalDateTime.now();

        try (
                Connection conn = dataSource.getConnection();

        ) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setLong(1, idOrder);
                pstmt.setObject(2, createdAt);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new Order();
    }

    public Order CreateOrder(Long idOrder) {
       Order order =  insertOrder(idOrder);
       orderHistoryDao.saveStatusHistory(idOrder, new StatusHistory(LocalDateTime.now(), OrderStatus.CREATED));
       return getById(idOrder);
    }

    public List<DishSales> getDishesSold() {
        String sql = """
        SELECT 
          d.id_dish AS dishIdentifier,
          d.name AS dishName,
          SUM(da.quantity) AS quantitySold
        FROM DishOrder da
        JOIN Dish d ON da.id_dish = d.id_dish
        WHERE da.id_dish_order IN (
            SELECT id_dish_order
            FROM (
                SELECT DISTINCT ON (id_dish_order)
                    id_dish_order, new_status
                FROM DishOrderStatusHistory
                ORDER BY id_dish_order, change_date DESC
            ) latest_status
            WHERE new_status = 'DELIVERED'
        )
        GROUP BY d.id_dish, d.name
        ORDER BY quantitySold DESC
        """;

        List<DishSales> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DishSales sale = new DishSales(
                        rs.getLong("dishIdentifier"),
                        rs.getString("dishName"),
                        rs.getInt("quantitySold")
                );
                result.add(sale);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }



}
