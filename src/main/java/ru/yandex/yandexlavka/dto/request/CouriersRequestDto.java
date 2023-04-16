package ru.yandex.yandexlavka.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.yandex.yandexlavka.model.CourierType;

import java.util.List;

public class CouriersRequestDto {

    @JsonProperty("couriers")
    private List<CourierRequestDto> couriersRequestDto;

    public List<CourierRequestDto> getCouriersRequestDto() {
        return couriersRequestDto;
    }

    public static class CourierRequestDto {

        @JsonProperty("courier_type")
        private CourierType courierType;

        @JsonProperty("regions")
        private List<Long> regions;

        @JsonProperty("working_hours")
        private List<String> workingTime;

        // ** Getters **
        public CourierType getCourierType() {
            return courierType;
        }

        public List<Long> getRegions() {
            return regions;
        }

        public List<String> getWorkingTime() {
            return workingTime;
        }
    }
}
