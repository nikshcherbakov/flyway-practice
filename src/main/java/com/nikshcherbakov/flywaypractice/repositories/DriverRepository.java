package com.nikshcherbakov.flywaypractice.repositories;

import com.nikshcherbakov.flywaypractice.models.Driver;
import com.nikshcherbakov.flywaypractice.models.Truck;
import com.nikshcherbakov.flywaypractice.repositories.dto.DriverTruckDto;
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
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DriverRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Driver> rowMapper = (rs, rowNum) -> {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        return new Driver(id, name, null);
    };

    public Driver save(Driver driver) {
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

        return driver;
    }

    private void joinTruckToDriver(Truck truck, Driver driver) {
        String joinTruckToDriverSql = "insert into driver_trucks values (?, ?)";
        jdbcTemplate.update(joinTruckToDriverSql, driver.getId(), truck.getId());
    }

    public Optional<Driver> findDriverById(Long id) {
        String sql = "select * from driver where id = ?";
        List<Driver> drivers = jdbcTemplate.query(sql, rowMapper, id);
        if (drivers.size() != 1) {
            return Optional.empty();
        }
        Driver driver = drivers.get(0);
        driver.setTrucks(findTrucksByDriver(driver));
        return Optional.of(driver);
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
        List<DriverTruckDto> driverTruckDtoList = jdbcTemplate.query(
                trucksByDriverSql,
                (rs, rowNum) -> new DriverTruckDto(rs.getLong("driver_id"), rs.getLong("trucks_id")),
                driver.getId()
        );
        for (DriverTruckDto driverTruckDto : driverTruckDtoList) {
            Long truckId = driverTruckDto.getTruckId();
            findTruckByTruckId(truckId).ifPresent(trucksByDriver::add);
        }
        return trucksByDriver;
    }

    private Optional<Truck> findTruckByTruckId(Long truckId) {
        String sql = "select * from truck where id = ?";
        List<Truck> trucks = jdbcTemplate.query(sql, (rs, rowNum) -> new Truck(
                    rs.getLong("id"),
                    rs.getString("brand"),
                    rs.getString("model")
                ),
                truckId);
        return trucks.size() == 1 ? Optional.of(trucks.get(0)) : Optional.empty();
    }

}
