package com.company.carSeller.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "engine", schema = "public", catalog = "CarSellerDB")
public class EngineEntity {
    private int id;
    private String engine;

    public EngineEntity(){

    }

    public EngineEntity(String engine) {
        this.engine = engine;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "engine", nullable = false, length = 50)
    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EngineEntity that = (EngineEntity) o;
        return Objects.equals(engine, that.engine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(engine);
    }

    @Override
    public String toString() {
        return "EngineEntity{" +
                "id=" + id +
                ", engine='" + engine + '\'' +
                '}';
    }
}
