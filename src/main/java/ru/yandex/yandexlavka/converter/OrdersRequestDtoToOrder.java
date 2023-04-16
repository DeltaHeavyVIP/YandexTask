package ru.yandex.yandexlavka.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.yandex.yandexlavka.dto.request.OrdersRequestDto;
import ru.yandex.yandexlavka.model.Order;
import ru.yandex.yandexlavka.utils.TimePeriodHelper;

@Component
public class OrdersRequestDtoToOrder implements Converter<OrdersRequestDto.OrderRequestDto, Order> {

    private final TimePeriodHelper timePeriodHelper;

    public OrdersRequestDtoToOrder(TimePeriodHelper timePeriodHelper) {
        this.timePeriodHelper = timePeriodHelper;
    }

    @Override
    public Order convert(OrdersRequestDto.OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setWeight(orderRequestDto.getWeight());
        order.setRegion(orderRequestDto.getRegions());
        order.setDeliveryTime(timePeriodHelper.getTimePeriodFromRequest(orderRequestDto.getDeliveryTime()));
        order.setCost(orderRequestDto.getCost());
        return order;
    }
}
