package ru.yandex.yandexlavka.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import ru.yandex.yandexlavka.exception.InvalidInputDataException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "couriers")
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "courier_type")
    private CourierType courierType;

    @Column(name = "region")
    private String region;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TimePeriod> workingTime;

    // ** Getters **
    public Long getId() {
        return id;
    }

    public CourierType getCourierType() {
        return courierType;
    }

    public List<Long> getRegionsList() {
        return Arrays.stream(region.split(",")).map(Long::parseLong).collect(Collectors.toCollection(ArrayList::new));
    }

    public String getRegions() {
        return region;
    }

    public Set<TimePeriod> getWorkingTime() {
        return workingTime;
    }

    // ** Setters **
    public void setId(Long id) {
        this.id = id;
    }

    public void setCourierType(CourierType courierType) {
        this.courierType = courierType;
    }

    public void setRegion(List<Long> regions) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Long region : regions) {
            if (region < 0) {
                throw new InvalidInputDataException();
            }
            stringBuffer.append(region).append(",");
        }
        this.region = stringBuffer.toString();
    }

    public void setWorkingTime(Set<TimePeriod> workingTime) {
        this.workingTime = workingTime;
    }
}
