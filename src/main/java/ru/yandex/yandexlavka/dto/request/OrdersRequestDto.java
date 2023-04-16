package ru.yandex.yandexlavka.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrdersRequestDto {

    @JsonProperty("orders")
    private List<OrderRequestDto> ordersRequestDto;

    // ** Getters **
    public List<OrderRequestDto> getOrdersRequestDto() {
        return ordersRequestDto;
    }

    public static class OrderRequestDto {
        @JsonProperty("weight")
        private Float weight;
        @JsonProperty("regions")
        private Integer region;
        @JsonProperty("delivery_hours")
        private List<String> deliveryTime;
        @JsonProperty("cost")
        private Integer cost;

        // ** Getters **
        public Float getWeight() {
            return weight;
        }

        public Integer getRegions() {
            return region;
        }

        public List<String> getDeliveryTime() {
            return deliveryTime;
        }

        public Integer getCost() {
            return cost;
        }

        // ** Override methods **
        @Override
        public String toString() {
            return " weight - " + this.weight +
                    " region - " + this.region +
                    " deliveryHour - " + this.deliveryTime.toString() +
                    " cost - " + this.cost;
        }
    }
}
