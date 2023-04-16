package ru.yandex.yandexlavka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.yandexlavka.model.Courier;

import java.util.List;

@Repository
public interface CourierRepo extends JpaRepository<Courier, Long> {

    List<Courier> findAllByOrderByIdAsc();

    List<Courier> findAllByOrderByCourierTypeDesc();

}
