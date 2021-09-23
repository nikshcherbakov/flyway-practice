package com.nikshcherbakov.flywaypractice.repositories;

import com.nikshcherbakov.flywaypractice.models.Driver;
import com.nikshcherbakov.flywaypractice.utils.DriverRow;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class DriverRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<DriverRow> rowMapper = (rs, rowNum) -> {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        Long truckId = rs.getLong("truck_id");
        return new DriverRow(id, name, truckId);
    };

    public boolean save(Driver driver) {
        String sql = "insert into driver (name, truck_id) values (?, ?)";
        int affectedRows = jdbcTemplate.update(sql, driver.getName(),
                driver.getTruck() != null ? driver.getTruck().getId() : null);
        return affectedRows == 1;
    }

    public DriverRow findDriverById(Long id) {
        String sql = "select * from driver where id = ?";
        List<DriverRow> drivers = jdbcTemplate.query(sql, rowMapper, id);
        return drivers.size() == 1 ? drivers.get(0) : null;
    }

    public List<DriverRow> findAllDrivers() {
        String sql = "select * from driver";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
