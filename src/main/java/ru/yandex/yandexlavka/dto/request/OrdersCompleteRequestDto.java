package ru.yandex.yandexlavka.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public class OrdersCompleteRequestDto {
    @JsonProperty("complete_info")
    private List<CompleteRequestDto> completeRequestDto;

    public List<CompleteRequestDto> getCompleteRequestDto() {
        return completeRequestDto;
    }

    public static class CompleteRequestDto {
        @JsonProperty("courier_id")
        private Long courierId;

        @JsonProperty("order_id")
        private Long orderId;

        @JsonProperty("complete_time")
        private Instant completeTime;

        // ** Getters **
        public Long getCourierId() {
            return courierId;
        }

        public Long getOrderId() {
            return orderId;
        }

        public Instant getCompleteTime() {
            return completeTime;
        }
    }
}
