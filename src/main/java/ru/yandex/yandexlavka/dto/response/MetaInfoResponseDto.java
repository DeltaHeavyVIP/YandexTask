package ru.yandex.yandexlavka.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.yandex.yandexlavka.model.CourierType;

import java.util.List;

public class MetaInfoResponseDto {

    @JsonProperty("courier_id")
    private Long courierId;

    @JsonProperty("courier_type")
    private CourierType courierType;

    @JsonProperty("regions")
    private List<Long> regions;

    @JsonProperty("working_hours")
    private List<String> workingTime;

    @JsonProperty("rating")
    private Double rating;

    @JsonProperty("earnings")
    private Long earnings;

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public void setCourierType(CourierType courierType) {
        this.courierType = courierType;
    }

    public void setRegions(List<Long> regions) {
        this.regions = regions;
    }

    public void setWorkingTime(List<String> workingTime) {
        this.workingTime = workingTime;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setEarnings(Long earnings) {
        this.earnings = earnings;
    }
}
