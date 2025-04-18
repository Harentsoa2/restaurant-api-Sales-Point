package edu.hei.school.restaurant.repository.dao;

import edu.hei.school.restaurant.repository.DataSource;
import edu.hei.school.restaurant.entity.PriceIngredient;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PriceDao {
    private DataSource dataSource;

    public PriceDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public List<PriceIngredient> getByIngredient(Long idIngredient) {
        String sql = "select id_price, id_ingredient, unit_price, effective_date from price where id_ingredient = ?";
        List<PriceIngredient> priceIngredients = new ArrayList<>();

        try (
                Connection conn1 = dataSource.getConnection();
                PreparedStatement pstmt = conn1.prepareStatement(sql);
        ) {
            pstmt.setLong(1, idIngredient);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PriceIngredient priceIngredient = new PriceIngredient();
                    priceIngredient.setId(rs.getLong("id_price"));
                    priceIngredient.setPrice(rs.getDouble("unit_price"));
                    priceIngredient.setEffectiveDate(rs.getObject("effective_date", LocalDateTime.class));
                    priceIngredients.add(priceIngredient);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return priceIngredients;
    }


    public List<PriceIngredient> saveAll(List<PriceIngredient> priceIngredients, Long idIngredient){
        List<PriceIngredient> priceIngredients1 = new ArrayList<>();
        for(PriceIngredient priceIngredient : priceIngredients){
            saveOne(priceIngredient, idIngredient);
            priceIngredients1.add(priceIngredient);
        }
        return priceIngredients1;
    }

    public PriceIngredient saveOne(PriceIngredient priceIngredient, Long idIngredient) {

        String sql = "Insert into price (id_ingredient, unit_price, effective_date, id_price) values (?, ?, ?, ?)"+
                      "ON CONFLICT (id_price) do update set id_ingredient = excluded.id_ingredient, unit_price = excluded.unit_price, effective_date = excluded.effective_date";

        try(Connection connection = dataSource.getConnection()) {
            try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setLong(1, idIngredient);
                pstmt.setDouble(2, priceIngredient.getPrice());
                pstmt.setTimestamp(3, Timestamp.valueOf(priceIngredient.getEffectiveDate()));
                pstmt.setLong(4, priceIngredient.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return priceIngredient;
    }


}