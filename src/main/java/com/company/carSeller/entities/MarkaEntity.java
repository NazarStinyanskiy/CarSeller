package com.company.carSeller.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "marka", schema = "public", catalog = "CarSellerDB")
public class MarkaEntity {
    private int id;
    private String marka;

    public MarkaEntity(){

    }

    public MarkaEntity(String marka) {
        this.marka = marka;
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
    @Column(name = "marka", nullable = false, length = 50)
    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkaEntity that = (MarkaEntity) o;
        return Objects.equals(marka, that.marka);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marka);
    }

    @Override
    public String toString() {
        return "MarkaEntity{" +
                "id=" + id +
                ", marka='" + marka + '\'' +
                '}';
    }
}
