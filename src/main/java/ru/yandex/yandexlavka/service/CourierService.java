package ru.yandex.yandexlavka.service;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.yandexlavka.dto.request.CouriersRequestDto;
import ru.yandex.yandexlavka.dto.response.AssignResponseDto;
import ru.yandex.yandexlavka.dto.response.CouriersResponseDto;
import ru.yandex.yandexlavka.dto.response.MetaInfoResponseDto;
import ru.yandex.yandexlavka.exception.ResourceNotFoundException;
import ru.yandex.yandexlavka.model.Courier;
import ru.yandex.yandexlavka.model.Order;
import ru.yandex.yandexlavka.repository.CourierRepo;
import ru.yandex.yandexlavka.repository.OrderRepo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourierService {

    private final ConversionService conversionService;
    private final CourierRepo courierRepo;

    private final OrderRepo orderRepo;

    public CourierService(ConversionService conversionService, CourierRepo courierRepo, OrderRepo orderRepo) {
        this.conversionService = conversionService;
        this.courierRepo = courierRepo;
        this.orderRepo = orderRepo;
    }

    public AssignResponseDto getDistributeOrders(LocalDate date, Long courierId) {
        List<Order> list = orderRepo.findAllByDistributionDateEqualsAndCourier_IdOrderByGroupId(Date.valueOf(date), courierId);
        return new AssignResponseDto(conversionService, Date.valueOf(date), list,
                list.stream().map(element -> element.getCourier().getId()).collect(Collectors.toSet()));
    }

    public CouriersResponseDto getCouriers(Integer limit, Integer offset) {
        CouriersResponseDto couriersResponseDto = new CouriersResponseDto();

        List<Courier> couriers = courierRepo.findAllByOrderByIdAsc();
        couriersResponseDto.setCouriersRequestDto(couriers.subList(Math.min(couriers.size(), offset), Math.min(couriers.size(), offset + limit)).stream()
                .map(element -> conversionService.convert(element, CouriersResponseDto.CourierResponseDto.class)).collect(Collectors.toList()));

        couriersResponseDto.setLimit(limit);
        couriersResponseDto.setOffset(offset);
        return couriersResponseDto;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<CouriersResponseDto.CourierResponseDto> addCouriers(List<CouriersRequestDto.CourierRequestDto> couriersRequestDto) {
        List<Courier> couriers = couriersRequestDto.stream().map(element -> conversionService.convert(element, Courier.class)).collect(Collectors.toList());
        return courierRepo.saveAllAndFlush(couriers).stream().map(element -> conversionService.convert(element, CouriersResponseDto.CourierResponseDto.class))
                .collect(Collectors.toList());
    }

    public CouriersResponseDto.CourierResponseDto getCourierById(Long courierId) {
        return conversionService.convert(courierRepo.findById(courierId).orElseThrow(ResourceNotFoundException::new), CouriersResponseDto.CourierResponseDto.class);
    }

    public MetaInfoResponseDto payrollAndRatingCourier(Long courierId, LocalDate startDate, LocalDate endDate) {
        Courier courier = courierRepo.findById(courierId).orElseThrow(ResourceNotFoundException::new);
        List<Order> orders = orderRepo.findSumCostByCourier_IdAndCompletedTimeBetween(courierId,
                Date.valueOf(startDate), Date.valueOf(endDate));

        if (orders.size() == 0) return null;

        MetaInfoResponseDto metaInfoResponseDto = conversionService.convert(courier, MetaInfoResponseDto.class);

        double countHours = ChronoUnit.DAYS.between(startDate, endDate) * 24;
        double rating = (orders.size() / countHours) * courier.getCourierType().getCoefficientRating();
        metaInfoResponseDto.setRating(rating);

        long sum = orders.stream().map(Order::getCost).reduce(0, Integer::sum);
        sum *= courier.getCourierType().getCoefficientEarn();
        metaInfoResponseDto.setEarnings(sum);

        return metaInfoResponseDto;
    }
}
