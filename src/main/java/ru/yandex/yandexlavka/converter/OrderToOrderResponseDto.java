package ru.yandex.yandexlavka.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.yandex.yandexlavka.dto.response.OrderResponseDto;
import ru.yandex.yandexlavka.model.Order;
import ru.yandex.yandexlavka.utils.TimePeriodHelper;

@Component
public class OrderToOrderResponseDto implements Converter<Order, OrderResponseDto> {

    private final TimePeriodHelper timePeriodHelper;

    public OrderToOrderResponseDto(TimePeriodHelper timePeriodHelper) {
        this.timePeriodHelper = timePeriodHelper;
    }

    @Override
    public OrderResponseDto convert(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderId(order.getId());
        orderResponseDto.setWeight(order.getWeight());
        orderResponseDto.setRegion(order.getRegion());
        orderResponseDto.setCost(order.getCost());
        orderResponseDto.setDeliveryTime(timePeriodHelper.getTimePeriodForResponse(order.getDeliveryTime()));
        orderResponseDto.setCompletedTime(order.getCompletedTime() == null ? null : order.getCompletedTime().toInstant());
        return orderResponseDto;
    }
}
