package edu.hei.school.restaurant.repository.dao;

import edu.hei.school.restaurant.entity.StatusHistory;
import edu.hei.school.restaurant.repository.DataSource;
import edu.hei.school.restaurant.repository.dao.mapper.OrderStatusMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderHistoryDao {
    private DataSource dataSource ;
    private OrderStatusMapper orderStatusMapper;
    private DishOrderHistoryDao dishOrderHistoryDao;

    public OrderHistoryDao(DataSource dataSource, OrderStatusMapper orderStatusMapper, DishOrderHistoryDao dishOrderHistoryDao) {
        this.dataSource = dataSource;
        this.orderStatusMapper = orderStatusMapper;
        this.dishOrderHistoryDao = dishOrderHistoryDao;
    }

    public List<StatusHistory> getAll() {
        List<StatusHistory> orderHistories = new ArrayList<StatusHistory>();
        String sql = "select id_order, new_status, change_date from orderstatushistory";

            try(Connection connection = dataSource.getConnection();
                    PreparedStatement pstmp = connection.prepareStatement(sql);
                ResultSet rs = pstmp.executeQuery()
            ) {
                while(rs.next()) {
                    StatusHistory statusHistory = new StatusHistory();
                    statusHistory.setStatusChangeDate(rs.getObject("change_date", LocalDateTime.class));
                    statusHistory.setStatus(orderStatusMapper.mapFromResultSet(rs.getString("new_status")));
                    orderHistories.add(statusHistory);
                }
            }catch(SQLException e) {
                throw new RuntimeException(e);
            }

        return orderHistories;
    }

    public List<StatusHistory> getAllByOrderId(Long reference) {
        List<StatusHistory> dishOrderHistories = new ArrayList<StatusHistory>();
        String sql = "select id_order, new_status, change_date from orderstatushistory where id_order = ?";

        Connection connection = dataSource.getConnection();

        try(PreparedStatement pstmp = connection.prepareStatement(sql);
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

    public List<Long> findDishOrderIdsByOrderId(Long orderId) {
        List<Long> ids = new ArrayList<>();
        String sql = "SELECT id_dish_order FROM dishorder WHERE id_order = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ids.add(rs.getLong("id_dish_order"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ids;
    }


    public StatusHistory saveStatusHistory(Long reference, StatusHistory statusHistory) {
        String sql = "insert into orderStatusHistory (id_order, new_status, change_date) values (?, ?, ?)"+
                        "on conflict (id_order, new_status) do update set change_date = excluded.change_date";

        List<Long> idOrderDishes = findDishOrderIdsByOrderId(reference);

        try(Connection connection = dataSource.getConnection();
            PreparedStatement pstmtp = connection.prepareStatement(sql);
        ){
            pstmtp.setLong(1, reference);
            pstmtp.setObject(2, statusHistory.getStatus().name(), Types.OTHER);
            pstmtp.setTimestamp(3, Timestamp.valueOf(statusHistory.getStatusChangeDate()));
            pstmtp.executeUpdate();

            for(Long id: idOrderDishes) {
                dishOrderHistoryDao.saveStatusHistory(id, new StatusHistory(statusHistory.getStatusChangeDate(), statusHistory.getStatus()));
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statusHistory;
    }







}
