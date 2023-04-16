package ru.yandex.yandexlavka.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.yandex.yandexlavka.dto.response.CouriersResponseDto;
import ru.yandex.yandexlavka.model.Courier;
import ru.yandex.yandexlavka.utils.TimePeriodHelper;

@Component
public class CourierToCourierResponseDto implements Converter<Courier, CouriersResponseDto.CourierResponseDto> {

    private final TimePeriodHelper timePeriodHelper;

    public CourierToCourierResponseDto(TimePeriodHelper timePeriodHelper) {
        this.timePeriodHelper = timePeriodHelper;
    }

    @Override
    public CouriersResponseDto.CourierResponseDto convert(Courier courier) {
        CouriersResponseDto.CourierResponseDto courierResponseDto = new CouriersResponseDto.CourierResponseDto();
        courierResponseDto.setCourierId(courier.getId());
        courierResponseDto.setCourierType(courier.getCourierType());
        courierResponseDto.setWorkingTime(timePeriodHelper.getTimePeriodForResponse(courier.getWorkingTime()));
        courierResponseDto.setRegions(courier.getRegionsList());
        return courierResponseDto;
    }
}
