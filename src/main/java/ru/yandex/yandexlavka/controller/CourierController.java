package ru.yandex.yandexlavka.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.yandexlavka.dto.request.CouriersRequestDto;
import ru.yandex.yandexlavka.exception.InvalidInputDataException;
import ru.yandex.yandexlavka.service.CourierService;

import java.time.LocalDate;

@RestController
@CrossOrigin
@RequestMapping(value = "/couriers")
public class CourierController {

    private final CourierService courierService;

    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @GetMapping("/assignments")
    public ResponseEntity<?> getDistributeOrders(@RequestParam(name = "date", required = false) LocalDate date,
                                                 @RequestParam(name = "courier_id", required = false) Long courierId) {
        return new ResponseEntity<>(courierService.getDistributeOrders(date, courierId), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getCouriers(@RequestParam(name = "limit", defaultValue = "1") Integer limit,
                                         @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        if (limit < 1 || offset < 0) throw new InvalidInputDataException();
        return new ResponseEntity<>(courierService.getCouriers(limit, offset), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addCouriers(@RequestBody CouriersRequestDto couriersRequestDto) {
        return new ResponseEntity<>(courierService.addCouriers(couriersRequestDto.getCouriersRequestDto()), HttpStatus.OK);
    }

    @GetMapping("/{courier_id}")
    public ResponseEntity<?> getCourierById(@PathVariable(name = "courier_id") Long courierId) {
        return new ResponseEntity<>(courierService.getCourierById(courierId), HttpStatus.OK);
    }

    @GetMapping("/meta-info/{courier_id}")
    public ResponseEntity<?> payrollAndRatingCourier(@PathVariable(name = "courier_id") Long courierId,
                                                     @RequestParam(name = "startDate") LocalDate startDate,
                                                     @RequestParam(name = "endDate") LocalDate endDate) {
        return new ResponseEntity<>(courierService.payrollAndRatingCourier(courierId, startDate, endDate), HttpStatus.OK);
    }

}
