package com.company.carSeller.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "car_info")
public class CarInfoEntity {
    private int id;
    private int price;
    private String photoUrl;
    private String description;
    private String title;

    private ColorEntity colorEntity;
    private EngineEntity engineEntity;
    private MarkaEntity markaEntity;
    private UserInfoEntity userInfoEntity;

    private Set<ModificationEntity> modificationEntity;
    private InvoiceEntity invoiceEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "price", nullable = false)
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "photo_url", length = 255)
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Basic
    @Column(name= "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }




    @OneToOne(mappedBy = "carInfoEntity")
    public InvoiceEntity getInvoiceEntity() {
        return invoiceEntity;
    }

    public void setInvoiceEntity(InvoiceEntity invoiceEntity) {
        this.invoiceEntity = invoiceEntity;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "color_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public ColorEntity getColorEntity() {
        return colorEntity;
    }

    public void setColorEntity(ColorEntity colorEntity) { this.colorEntity = colorEntity; }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "engine_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public EngineEntity getEngineEntity() {
        return engineEntity;
    }

    public void setEngineEntity(EngineEntity engineEntity) {
        this.engineEntity = engineEntity;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "marka_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public MarkaEntity getMarkaEntity() {
        return markaEntity;
    }

    public void setMarkaEntity(MarkaEntity markaEntity) {
        this.markaEntity = markaEntity;
    }

    @ManyToOne
    @JoinColumn(name = "user_info_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public UserInfoEntity getUserInfoEntity() {
        return userInfoEntity;
    }

    public void setUserInfoEntity(UserInfoEntity userInfoEntity) {
        this.userInfoEntity = userInfoEntity;
    }

    @ManyToMany
    @JoinTable(
        name = "car_info_modification",
        joinColumns = {@JoinColumn(name = "car_info_id")},
        inverseJoinColumns = {@JoinColumn(name = "modification_id")}
    )
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public Set<ModificationEntity> getModificationEntity() {
        return modificationEntity;
    }

    public void setModificationEntity(Set<ModificationEntity> modificationEntity) {
        this.modificationEntity = modificationEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarInfoEntity that = (CarInfoEntity) o;

        if (id != that.id) return false;
        if (price != that.price) return false;
        if (photoUrl != null ? !photoUrl.equals(that.photoUrl) : that.photoUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + price;
        result = 31 * result + (photoUrl != null ? photoUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CarInfoEntity{" +
                "id=" + id +
                ", price=" + price +
                ", photoUrl='" + photoUrl + '\'' +
                ", color_id=" + colorEntity +
                ", engineEntity=" + engineEntity +
                ", markaEntity=" + markaEntity +
                ", userInfoEntity=" + userInfoEntity +
                '}';
    }
}
