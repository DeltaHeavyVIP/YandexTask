package ru.yandex.yandexlavka.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.yandex.yandexlavka.dto.request.CouriersRequestDto;
import ru.yandex.yandexlavka.model.Courier;
import ru.yandex.yandexlavka.utils.TimePeriodHelper;

@Component
public class CourierRequestDtoToCourier implements Converter<CouriersRequestDto.CourierRequestDto, Courier> {

    private final TimePeriodHelper timePeriodHelper;

    public CourierRequestDtoToCourier(TimePeriodHelper timePeriodHelper) {
        this.timePeriodHelper = timePeriodHelper;
    }

    @Override
    public Courier convert(CouriersRequestDto.CourierRequestDto courierRequestDto) {
        Courier courier = new Courier();
        courier.setCourierType(courierRequestDto.getCourierType());
        courier.setRegion(courierRequestDto.getRegions());
        courier.setWorkingTime(timePeriodHelper.getTimePeriodFromRequest(courierRequestDto.getWorkingTime()));
        return courier;
    }
}
