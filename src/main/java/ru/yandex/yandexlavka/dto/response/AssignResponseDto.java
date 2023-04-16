package ru.yandex.yandexlavka.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.core.convert.ConversionService;
import ru.yandex.yandexlavka.model.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AssignResponseDto {

    @JsonProperty("date")
    private Date date;

    @JsonProperty("couriers")
    private List<AssignCourierResponseDto> couriers;

    public AssignResponseDto(ConversionService conversionService, Date date, List<Order> orders, Set<Long> couriersId) {
        this.date = date;
        List<AssignResponseDto.AssignCourierResponseDto> couriers = new ArrayList<>();
        for (Long courierId : couriersId) {
            AssignResponseDto.AssignCourierResponseDto courier =
                    new AssignResponseDto.AssignCourierResponseDto();
            courier.setCourierId(courierId);
            List<AssignResponseDto.AssignCourierResponseDto.GroupResponseDto> groups = new ArrayList<>();
            for (Long groupId : orders.stream().filter(element -> element.getCourier().getId().equals(courierId)).map(Order::getGroupId).collect(Collectors.toSet())) {
                AssignResponseDto.AssignCourierResponseDto.GroupResponseDto groupResponseDto = new AssignResponseDto.AssignCourierResponseDto.GroupResponseDto();
                groupResponseDto.setGroupOrderId(groupId);
                groupResponseDto.setOrders(orders.stream().filter(element -> element.getGroupId().equals(groupId)).map(element -> conversionService.convert(element, OrderResponseDto.class)).toList());
                groups.add(groupResponseDto);
            }
            courier.setGroups(groups);
            couriers.add(courier);
        }
        this.couriers = couriers;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCouriers(List<AssignCourierResponseDto> couriers) {
        this.couriers = couriers;
    }

    public static class AssignCourierResponseDto {

        @JsonProperty("courier_id")
        private Long courierId;

        @JsonProperty("orders")
        private List<GroupResponseDto> groups;

        public void setCourierId(Long courierId) {
            this.courierId = courierId;
        }

        public void setGroups(List<GroupResponseDto> groups) {
            this.groups = groups;
        }

        public static class GroupResponseDto {

            @JsonProperty("group_order_id")
            private Long groupOrderId;

            @JsonProperty("orders")
            private List<OrderResponseDto> orders;

            public void setGroupOrderId(Long groupOrderId) {
                this.groupOrderId = groupOrderId;
            }

            public void setOrders(List<OrderResponseDto> orders) {
                this.orders = orders;
            }
        }
    }
}
