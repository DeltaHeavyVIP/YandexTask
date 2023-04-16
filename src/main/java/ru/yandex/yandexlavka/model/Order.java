package ru.yandex.yandexlavka.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Max(40)
    @Min(0)
    @Column(name = "weight")
    private Float weight;

    @Column(name = "region")
    private Integer region;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TimePeriod> deliveryTime;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "completed_time")
    private Date completedTime;

    @Column(name = "distribution_date")
    private java.sql.Date distributionDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Courier courier;

    // ** Getters **
    public Long getId() {
        return id;
    }

    public Float getWeight() {
        return weight;
    }

    public Integer getRegion() {
        return region;
    }

    public Set<TimePeriod> getDeliveryTime() {
        return deliveryTime;
    }

    public Integer getCost() {
        return cost;
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    public Long getGroupId() {
        return groupId;
    }

    public java.sql.Date getDistributionDate() {
        return distributionDate;
    }

    public Courier getCourier() {
        return courier;
    }

    // ** Setters **
    public void setId(Long id) {
        this.id = id;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public void setDeliveryTime(Set<TimePeriod> deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public void setDistributionDate(java.sql.Date distributionDate) {
        this.distributionDate = distributionDate;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }
}
