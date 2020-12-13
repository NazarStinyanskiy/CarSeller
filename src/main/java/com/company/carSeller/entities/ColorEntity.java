package com.company.carSeller.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "color", schema = "public", catalog = "CarSellerDB")
public class ColorEntity {
    private int id;
    private String color;

    public ColorEntity(String color) {
        this.color = color;
    }

    public ColorEntity(){

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
    @Column(name = "color", nullable = false, length = 50)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorEntity that = (ColorEntity) o;
        return Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    @Override
    public String toString() {
        return "ColorEntity{" +
                "id=" + id +
                ", color='" + color + '\'' +
                '}';
    }
}
