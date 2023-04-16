package ru.yandex.yandexlavka.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Time;

@Entity
@Table(name = "time_period")
public class TimePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time endTime;

    // ** Constructor **
    public TimePeriod() {
    }

    public TimePeriod(Time startTime, Time endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // ** Getters **
    public Integer getId() {
        return id;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    // ** Setters **
    public void setId(Integer id) {

        this.id = id;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
