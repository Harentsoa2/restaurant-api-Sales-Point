package edu.hei.school.restaurant.repository.dao;

import edu.hei.school.restaurant.dto.HistoryDish;
import edu.hei.school.restaurant.entity.StatusHistory;
import edu.hei.school.restaurant.repository.DataSource;
import edu.hei.school.restaurant.repository.dao.mapper.OrderStatusMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishOrderHistoryDao {
    private DataSource dataSource;
    private OrderStatusMapper orderStatusMapper;

    public DishOrderHistoryDao(DataSource dataSource, OrderStatusMapper orderStatusMapper) {
        this.dataSource = dataSource;
        this.orderStatusMapper = orderStatusMapper;
    }

    public List<StatusHistory> getAll() {
        List<StatusHistory> dishOrderHistories = new ArrayList<StatusHistory>();
        String sql = "select id_order, new_status, change_date from dishorderstatushistory";
        Connection connection = dataSource.getConnection();
        try(PreparedStatement pstmp = connection.prepareStatement(sql);
            ResultSet rs = pstmp.executeQuery()
        ) {
            while(rs.next()) {
                StatusHistory statusHistory = new StatusHistory();
                statusHistory.setStatusChangeDate(rs.getObject("change_date", LocalDateTime.class));
                statusHistory.setStatus(orderStatusMapper.mapFromResultSet(rs.getString("new_status")));
                dishOrderHistories.add(statusHistory);
            }
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return dishOrderHistories;
    }

    public List<HistoryDish> getAllByIdDish(Long id) {
        List<HistoryDish> dishOrderHistories = new ArrayList<HistoryDish>();
        String sql = "SELECT\n" +
                "    dsh.id_status_history,\n" +
                "    dsh.id_dish_order,\n" +
                "    dsh.new_status,\n" +
                "    dsh.change_date,\n" +
                "    dorder.id_order,\n" +
                "    dorder.id_dish,\n" +
                "    dorder.quantity,\n" +
                "    dorder.created_at\n" +
                "FROM DishOrderStatusHistory dsh\n" +
                "         JOIN DishOrder dorder ON dsh.id_dish_order = dorder.id_dish_order\n" +
                "WHERE dorder.id_dish = ?";

        Connection connection = dataSource.getConnection();
        try(PreparedStatement pstmp = connection.prepareStatement(sql);

        ) {
            pstmp.setLong(1, id);
            try(ResultSet rs = pstmp.executeQuery()) {
                while(rs.next()) {
                    HistoryDish statusHistory = new HistoryDish();
                    statusHistory.setStatusChangeDate(rs.getObject("change_date", LocalDateTime.class));
                    statusHistory.setStatus(orderStatusMapper.mapFromResultSet(rs.getString("new_status")));
                    statusHistory.setIdDishOrder(rs.getLong("id_dish_order"));
                    statusHistory.setIdDish(rs.getLong("id_dish"));
                    dishOrderHistories.add(statusHistory);
                }
            }
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return dishOrderHistories;
    }

    public List<StatusHistory> getAllByOrderId(Long reference) {
        List<StatusHistory> dishOrderHistories = new ArrayList<StatusHistory>();
        String sql = "select id_dish_order, new_status, change_date from dishorderstatushistory where id_dish_order = ?";

        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmp = connection.prepareStatement(sql);
        ) {
            pstmp.setLong(1, reference);
            try(ResultSet rs = pstmp.executeQuery()) {
                while (rs.next()) {
                    StatusHistory statusHistory = new StatusHistory();
                    statusHistory.setStatusChangeDate(rs.getObject("change_date", LocalDateTime.class));
                    statusHistory.setStatus(orderStatusMapper.mapFromResultSet(rs.getString("new_status")));
                    dishOrderHistories.add(statusHistory);
                }
            }
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return dishOrderHistories;
    }


    public Long getDishOrderId(Long orderId, Long dishId) {
        String sql = "SELECT id_dish_order FROM dishorder WHERE id_order = ? AND id_dish = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, orderId);
            stmt.setLong(2, dishId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id_dish_order");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public StatusHistory saveStatusHistory(Long idDishOrder, StatusHistory statusHistory) {
        String insertDishStatusSql = """
        INSERT INTO DishOrderStatusHistory (id_dish_order, new_status, change_date)
        VALUES (?, ?, ?)
        ON CONFLICT (id_dish_order, new_status)
        DO UPDATE SET change_date = EXCLUDED.change_date
        """;

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);


            try (PreparedStatement pstmt = conn.prepareStatement(insertDishStatusSql)) {
                pstmt.setLong(1, idDishOrder);
                pstmt.setObject(2, statusHistory.getStatus().name(), Types.OTHER);
                pstmt.setTimestamp(3, Timestamp.valueOf(statusHistory.getStatusChangeDate()));
                pstmt.executeUpdate();
            }

            Long idOrder = null;
            try (PreparedStatement stmt = conn.prepareStatement("""
            SELECT id_order FROM DishOrder WHERE id_dish_order = ?
        """)) {
                stmt.setLong(1, idDishOrder);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        idOrder = rs.getLong("id_order");
                    }
                }
            }

            if (idOrder != null) {

                try (PreparedStatement stmt = conn.prepareStatement("""
                SELECT DISTINCT new_status FROM (
                    SELECT DISTINCT ON (id_dish_order) id_dish_order, new_status
                    FROM DishOrderStatusHistory
                    WHERE id_dish_order IN (
                        SELECT id_dish_order FROM DishOrder WHERE id_order = ?
                    )
                    ORDER BY id_dish_order, change_date DESC
                ) AS latest_statuses
            """)) {
                    stmt.setLong(1, idOrder);
                    try (ResultSet rs = stmt.executeQuery()) {
                        List<String> distinctStatuses = new ArrayList<>();
                        while (rs.next()) {
                            distinctStatuses.add(rs.getString("new_status"));
                        }

                        if (distinctStatuses.size() == 1) {

                            try (PreparedStatement insertOrderHistory = conn.prepareStatement("""
                            INSERT INTO OrderStatusHistory (id_order, new_status, change_date)
                            VALUES (?, ?, ?)
                            ON CONFLICT (id_order, new_status)
                            DO UPDATE SET change_date = EXCLUDED.change_date
                        """)) {
                                insertOrderHistory.setLong(1, idOrder);
                                insertOrderHistory.setObject(2, distinctStatuses.get(0), Types.OTHER);
                                insertOrderHistory.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                                insertOrderHistory.executeUpdate();
                            }
                        }
                    }
                }
            }

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return statusHistory;
    }






}

