package com.nikshcherbakov.flywaypractice.models;

import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Truck {

    private Long id;
    private String brand;
    private String model;

    public Truck(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Truck truck = (Truck) o;
        return Objects.equals(id, truck.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
