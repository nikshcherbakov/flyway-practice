package com.nikshcherbakov.flywaypractice.repositories;

import com.nikshcherbakov.flywaypractice.models.Truck;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TruckRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Truck> rowMapper = (rs, rowNum) -> {
        Long id = rs.getLong("id");
        String brand = rs.getString("brand");
        String model = rs.getString("model");
        return new Truck(id, brand, model);
    };

    public boolean save(Truck truck) {
        String sql = "insert into truck (brand, model) values (?, ?)";
        int affectedRows = jdbcTemplate.update(sql, truck.getBrand(), truck.getModel());
        return affectedRows == 1;
    }

    public Truck findTruckById(long id) {
        String sql = "select * from truck where id = ?";
        List<Truck> trucks = jdbcTemplate.query(sql, rowMapper, id);
        return trucks.size() == 1 ? trucks.get(0) : null;
    }

    public List<Truck> findAllTrucks() {
        String sql = "select * from truck";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
