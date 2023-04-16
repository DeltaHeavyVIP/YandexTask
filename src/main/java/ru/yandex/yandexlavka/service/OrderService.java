package ru.yandex.yandexlavka.service;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.yandexlavka.dto.request.OrdersCompleteRequestDto;
import ru.yandex.yandexlavka.dto.request.OrdersRequestDto;
import ru.yandex.yandexlavka.dto.response.AssignResponseDto;
import ru.yandex.yandexlavka.dto.response.OrderResponseDto;
import ru.yandex.yandexlavka.exception.InvalidInputDataException;
import ru.yandex.yandexlavka.exception.ResourceNotFoundException;
import ru.yandex.yandexlavka.model.Courier;
import ru.yandex.yandexlavka.model.Order;
import ru.yandex.yandexlavka.repository.CourierRepo;
import ru.yandex.yandexlavka.repository.OrderRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final ConversionService conversionService;
    private final OrderRepo orderRepo;
    private final CourierRepo courierRepo;

    public OrderService(OrderRepo orderRepo, ConversionService conversionService, CourierRepo courierRepo) {
        this.orderRepo = orderRepo;
        this.conversionService = conversionService;
        this.courierRepo = courierRepo;
    }

    public List<OrderResponseDto> getOrders(Integer limit, Integer offset) {
        List<Order> orders = orderRepo.findAllByOrderByIdAsc();
        return orders.subList(Math.min(orders.size(), offset), Math.min(orders.size(), offset + limit)).stream()
                .map(element -> conversionService.convert(element, OrderResponseDto.class)).collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<OrderResponseDto> addOrders(List<OrdersRequestDto.OrderRequestDto> ordersRequestDto) {
        List<Order> orders = ordersRequestDto.stream().map(element -> conversionService.convert(element, Order.class)).collect(Collectors.toList());
        return orderRepo.saveAllAndFlush(orders).stream().map(element -> conversionService.convert(element, OrderResponseDto.class)).collect(Collectors.toList());
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<OrderResponseDto> completeOrders(List<OrdersCompleteRequestDto.CompleteRequestDto> completesRequestDto) {
        List<OrderResponseDto> orderResponseDto = new ArrayList<>();
        for (OrdersCompleteRequestDto.CompleteRequestDto completeRequestDto : completesRequestDto) {
            Order order = orderRepo.findByIdAndCompletedTimeIsNull(completeRequestDto.getOrderId()).orElseThrow(InvalidInputDataException::new);
            if (!order.getCourier().getId().equals(completeRequestDto.getCourierId()))
                throw new InvalidInputDataException();
            order.setCompletedTime(Date.from(completeRequestDto.getCompleteTime()));
            orderResponseDto.add(conversionService.convert(orderRepo.save(order), OrderResponseDto.class));
        }
        return orderResponseDto;
    }

    @Transactional
    public AssignResponseDto distributeOrdersByCouriers(LocalDate dateRequest) {
        java.sql.Date date = dateRequest == null ? new java.sql.Date(System.currentTimeMillis()) : java.sql.Date.valueOf(dateRequest);

        Long maxGroupId = orderRepo.findMaxGroupId().orElse(0L);
        List<Order> emptyOrders = orderRepo.findAllByDistributionDateIsNullAndCourierIsNull();
        List<Courier> couriers = courierRepo.findAllByOrderByIdAsc();

        if (couriers.isEmpty()) throw new ResourceNotFoundException();

        for (Order emptyOrder : emptyOrders) {
            emptyOrder.setGroupId(++maxGroupId);
            emptyOrder.setDistributionDate(date);
            emptyOrder.setCourier(couriers.get((int) (Math.random() * (couriers.size() - 1))));
        }

        List<Order> orders = orderRepo.saveAllAndFlush(emptyOrders);
        return new AssignResponseDto(conversionService, date, orders,
                orders.stream().map(element -> element.getCourier().getId()).collect(Collectors.toSet()));
    }

    //TODO
//    public Integer distributeOrdersByCouriers(LocalDate dateRequest) {
//        java.sql.Date date = getSqlDateFromString(dateRequest);
//        List<Courier> couriers = courierRepo.findAllByOrderByCourierTypeDesc();
//        for (Courier courier : couriers) {
//            switch (courier.getCourierType()) {
//                case AUTO:
//                    getRightOrders(CourierType.AUTO.getWeight(), courier.getRegions());
//                    break;
//                case BIKE:
//
//                    break;
//                case FOOT:
//            }
//        }
//        return null;
//    }
//
//    private List<Order> getRightOrders(Float weight, String regions) {
//        ArrayList<String> list = new ArrayList<>();
//        for (Order order : orderRepo.findAllForDistribute(weight, regions)) {
//
//        }
//        return null;
//    }

    public OrderResponseDto getOrderById(Long orderId) {
        return conversionService.convert(orderRepo.findById(orderId).orElseThrow(ResourceNotFoundException::new), OrderResponseDto.class);
    }

}
