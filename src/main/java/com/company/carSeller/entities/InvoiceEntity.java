package com.company.carSeller.entities;

import javax.persistence.*;

@Entity
@Table(name = "invoice")
public class InvoiceEntity {
    private int id;
    private String customerName;
    private String sellerName;
    private String customerPhone;
    private String sellerPhone;

    private CarInfoEntity carInfoEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    @MapsId
    public CarInfoEntity getCarInfoEntity() {
        return carInfoEntity;
    }

    public void setCarInfoEntity(CarInfoEntity carInfoEntity) {
        this.carInfoEntity = carInfoEntity;
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
    @Column(name = "customer_name", nullable = false, length = 50)
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Basic
    @Column(name = "seller_name", nullable = false, length = 50)
    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    @Basic
    @Column(name = "customer_phone", nullable = false, length = 20)
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Basic
    @Column(name = "seller_phone", nullable = false, length = 20)
    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvoiceEntity that = (InvoiceEntity) o;

        if (id != that.id) return false;
        if (customerName != null ? !customerName.equals(that.customerName) : that.customerName != null) return false;
        if (sellerName != null ? !sellerName.equals(that.sellerName) : that.sellerName != null) return false;
        if (customerPhone != null ? !customerPhone.equals(that.customerPhone) : that.customerPhone != null)
            return false;
        if (sellerPhone != null ? !sellerPhone.equals(that.sellerPhone) : that.sellerPhone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (sellerName != null ? sellerName.hashCode() : 0);
        result = 31 * result + (customerPhone != null ? customerPhone.hashCode() : 0);
        result = 31 * result + (sellerPhone != null ? sellerPhone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InvoiceEntity{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", sellerPhone='" + sellerPhone + '\'' +
                ", carInfoEntity=" + carInfoEntity +
                '}';
    }
}
