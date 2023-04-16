package ru.yandex.yandexlavka.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.yandexlavka.dto.request.OrdersCompleteRequestDto;
import ru.yandex.yandexlavka.dto.request.OrdersRequestDto;
import ru.yandex.yandexlavka.exception.InvalidInputDataException;
import ru.yandex.yandexlavka.service.OrderService;

import java.time.LocalDate;

@RestController
@CrossOrigin
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public ResponseEntity<?> getOrders(@RequestParam(name = "limit", defaultValue = "1") Integer limit,
                                       @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        if (limit < 1 || offset < 0) throw new InvalidInputDataException();
        return new ResponseEntity<>(orderService.getOrders(limit, offset), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addOrders(@RequestBody OrdersRequestDto ordersRequestDto) {
        return new ResponseEntity<>(orderService.addOrders(ordersRequestDto.getOrdersRequestDto()), HttpStatus.OK);
    }

    @PostMapping("/complete")
    public ResponseEntity<?> completeOrders(@RequestBody OrdersCompleteRequestDto ordersCompleteRequestDto) {
        return new ResponseEntity<>(orderService.completeOrders(ordersCompleteRequestDto.getCompleteRequestDto()), HttpStatus.OK);
    }

    @PostMapping("/assign")
    public ResponseEntity<?> distributeOrdersByCouriers(@RequestParam(name = "date", required = false) LocalDate date) {
        return new ResponseEntity<>(orderService.distributeOrdersByCouriers(date), HttpStatus.OK);
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<?> getOrderById(@PathVariable(name = "order_id") Long orderId) {
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
    }
}
