package ru.yandex.yandexlavka.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.yandex.yandexlavka.model.CourierType;

import java.util.List;

public class CouriersResponseDto {

    @JsonProperty("couriers")
    private List<CourierResponseDto> couriersResponseDto;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;

    // ** Setters **
    public void setCouriersRequestDto(List<CourierResponseDto> couriersResponseDto) {
        this.couriersResponseDto = couriersResponseDto;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public static class CourierResponseDto {
        @JsonProperty("courier_id")
        private Long courierId;

        @JsonProperty("courier_type")
        private CourierType courierType;

        @JsonProperty("regions")
        private List<Long> regions;

        @JsonProperty("working_hours")
        private List<String> workingTime;

        // ** Setters **
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
    }

}
