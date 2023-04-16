package ru.yandex.yandexlavka.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.yandex.yandexlavka.dto.response.MetaInfoResponseDto;
import ru.yandex.yandexlavka.model.Courier;
import ru.yandex.yandexlavka.utils.TimePeriodHelper;

@Component
public class CourierToMetaInfoResponseDto implements Converter<Courier, MetaInfoResponseDto> {

    private final TimePeriodHelper timePeriodHelper;

    public CourierToMetaInfoResponseDto(TimePeriodHelper timePeriodHelper) {
        this.timePeriodHelper = timePeriodHelper;
    }

    @Override
    public MetaInfoResponseDto convert(Courier courier) {
        MetaInfoResponseDto metaInfoResponseDto = new MetaInfoResponseDto();
        metaInfoResponseDto.setCourierId(courier.getId());
        metaInfoResponseDto.setCourierType(courier.getCourierType());
        metaInfoResponseDto.setWorkingTime(timePeriodHelper.getTimePeriodForResponse(courier.getWorkingTime()));
        metaInfoResponseDto.setRegions(courier.getRegionsList());
        return metaInfoResponseDto;
    }
}
