package ru.yandex.yandexlavka.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public class OrderResponseDto {
    @JsonProperty("order_id")
    private Long orderId;
    @JsonProperty("weight")
    private Float weight;
    @JsonProperty("regions")
    private Integer region;
    @JsonProperty("delivery_hours")
    private List<String> deliveryTime;
    @JsonProperty("cost")
    private Integer cost;
    @JsonProperty("completed_time")
    private Instant completedTime;

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public void setDeliveryTime(List<String> deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public void setCompletedTime(Instant completedTime) {
        this.completedTime = completedTime;
    }
}
