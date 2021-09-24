package com.nikshcherbakov.flywaypractice.repositories;

import com.nikshcherbakov.flywaypractice.models.Driver;
import com.nikshcherbakov.flywaypractice.models.Truck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class DriverRepository {
    @Data
    @AllArgsConstructor
    private static class DriverTruck {
        private Long driverId;
        private Long truckId;
    }

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Driver> rowMapper = (rs, rowNum) -> {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        return new Driver(id, name, null);
    };

    private final RowMapper<DriverTruck> rowMapper2 = (rs, rowNum) ->
            new DriverTruck(rs.getLong("driver_id"), rs.getLong("trucks_id"));

    private final RowMapper<Truck> rowMapper3 = (rs, rowNum) -> {
        Long truckId = rs.getLong("id");
        String brand = rs.getString("brand");
        String model = rs.getString("model");
        return new Truck(truckId, brand, model);
    };

    public void save(Driver driver) {
        // Сохраняем водителя
        String saveDriverSql = "insert into driver (name) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(saveDriverSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, driver.getName());
            return ps;
        }, keyHolder);
        driver.setId((Long) keyHolder.getKey());

        // Сохраняем траки и присоединяем их к водителю
        List<Truck> trucks = driver.getTrucks();
        if (trucks != null) {
            for (Truck truck : trucks) {
                joinTruckToDriver(truck, driver);
            }
        }
    }

    private void joinTruckToDriver(Truck truck, Driver driver) {
        String joinTruckToDriverSql = "insert into driver_trucks values (?, ?)";
        jdbcTemplate.update(joinTruckToDriverSql, driver.getId(), truck.getId());
    }

    public Driver findDriverById(Long id) {
        String sql = "select * from driver where id = ?";
        List<Driver> drivers = jdbcTemplate.query(sql, rowMapper, id);
        if (drivers.size() != 1) {
            return null;
        }
        Driver driver = drivers.get(0);
        driver.setTrucks(findTrucksByDriver(driver));
        return driver;
    }

    public List<Driver> findAllDrivers() {
        String sql = "select * from driver";
        List<Driver> drivers = jdbcTemplate.query(sql, rowMapper);

        // Добавляем грузовики к водителям
        for (Driver driver : drivers) {
            driver.setTrucks(findTrucksByDriver(driver));
        }

        return drivers;
    }

    private List<Truck> findTrucksByDriver(Driver driver) {
        List<Truck> trucksByDriver = new ArrayList<>();
        String trucksByDriverSql = "select * from driver_trucks where driver_id = ?";
        List<DriverTruck> driverTruckList = jdbcTemplate.query(trucksByDriverSql, rowMapper2, driver.getId());
        for (DriverTruck driverTruck : driverTruckList) {
            Long truckId = driverTruck.getTruckId();
            trucksByDriver.add(findTruckByTruckId(truckId));
        }
        return trucksByDriver;
    }

    private Truck findTruckByTruckId(Long truckId) {
        String sql = "select * from truck where id = ?";
        return jdbcTemplate.query(sql, rowMapper3, truckId).get(0);
    }

}
