package com.nikshcherbakov.flywaypractice.models;

import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Driver {

    private Long id;
    private String name;
    private Truck truck;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Driver driver = (Driver) o;
        return Objects.equals(id, driver.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
