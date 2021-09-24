package com.nikshcherbakov.flywaypractice.repositories;

import com.nikshcherbakov.flywaypractice.models.Truck;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

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

    public Truck save(Truck truck) {
        String sql = "insert into truck (brand, model) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, truck.getBrand());
            ps.setString(2, truck.getModel());
            return ps;
        }, keyHolder);
        truck.setId((Long) keyHolder.getKey());
        return truck;
    }

    public Optional<Truck> findTruckById(long id) {
        String sql = "select * from truck where id = ?";
        List<Truck> trucks = jdbcTemplate.query(sql, rowMapper, id);
        return trucks.size() == 1 ? Optional.of(trucks.get(0)) : Optional.empty();
    }

    public List<Truck> findAllTrucks() {
        String sql = "select * from truck";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
