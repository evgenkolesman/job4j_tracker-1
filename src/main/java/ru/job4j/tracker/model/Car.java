package ru.job4j.tracker.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    @NotNull
    private int id;

    @Column(name = "model")
    private String model;

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "owner")
    private String owner;

    public static Car of(String model, Timestamp created, String owner) {
        Car car = new Car();
        car.model = model;
        car.created = created;
        car.owner = owner;
        return car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return id == car.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}