package edu.hei.school.restaurant.repository.dao;

import edu.hei.school.restaurant.repository.DataSource;
import edu.hei.school.restaurant.repository.dao.mapper.MovementTypeMapper;
import edu.hei.school.restaurant.repository.dao.mapper.UnitMapper;
import edu.hei.school.restaurant.entity.StockMovement;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StockMovementDao {
    private DataSource dataSource;
    private MovementTypeMapper movementTypeMapper;
    private UnitMapper unitMapper;

    public StockMovementDao(DataSource dataSource, MovementTypeMapper movementTypeMapper, UnitMapper unitMapper) {
        this.dataSource = dataSource;
        this.movementTypeMapper = movementTypeMapper;
        this.unitMapper = unitMapper;
    }

    public List<StockMovement> getByIngredient(Long idIngredient) {
        String sql = "select id_movement, id_ingredient, movement_type, quantity, unit, movement_date from StockMovement where id_ingredient = ?";
        List<StockMovement> stockMovements = new ArrayList<>();

        try(
                Connection connection1 = dataSource.getConnection();
            PreparedStatement pstmp = connection1.prepareStatement(sql);

        ) {
            pstmp.setLong(1, idIngredient);
                try(ResultSet rs = pstmp.executeQuery()){
                    while (rs.next()) {
                        StockMovement stockMovement = new StockMovement();
                        stockMovement.setId(rs.getLong("id_movement"));
                        stockMovement.setMovementType(movementTypeMapper.mapFromResultSet(rs.getString("movement_type")));
                        stockMovement.setUnity(unitMapper.mapFromResultSet(rs.getString("unit")));
                        stockMovement.setQuantity(new BigDecimal(rs.getInt("quantity")));
                        stockMovement.setMovementDate(rs.getObject("movement_date", LocalDateTime.class));
                        stockMovements.add(stockMovement);
                    }

                }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stockMovements;
    }


    public List<StockMovement> saveAll(List<StockMovement> stockMovements, Long idIngredient){
        List<StockMovement> stockMovements1 = new ArrayList<>();
        for(StockMovement stockMovement : stockMovements){
            saveOne(stockMovement, idIngredient);
            stockMovements1.add(stockMovement);
        }
        return stockMovements1;
    }

    public StockMovement saveOne(StockMovement stockMovement, Long idIngredient) {
        String sql = "Insert into stockMovement (id_ingredient, movement_type, quantity, unit, movement_date, id_movement) values (?, ?, ?, ?, ?, ?)";

        try(Connection connection = dataSource.getConnection()) {
            try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setLong(1, idIngredient);
                pstmt.setObject(2, stockMovement.getMovementType().name(), Types.OTHER);
                pstmt.setDouble(3, stockMovement.getQuantity().doubleValue());
                pstmt.setObject(4, stockMovement.getUnity().name(), Types.OTHER);
                pstmt.setTimestamp(5, Timestamp.valueOf(stockMovement.getMovementDate()));
                pstmt.setLong(6, stockMovement.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stockMovement;
    }

}
