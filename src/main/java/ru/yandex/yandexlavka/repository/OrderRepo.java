package ru.yandex.yandexlavka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yandex.yandexlavka.model.Order;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderByIdAsc();

    Optional<Order> findByIdAndCompletedTimeIsNull(Long Id);

    List<Order> findSumCostByCourier_IdAndCompletedTimeBetween(Long courier_id, java.util.Date startTime,
                                                               java.util.Date endTime);

    @Query(value = "select max(group_id) from orders", nativeQuery = true)
    Optional<Long> findMaxGroupId();

    List<Order> findAllByDistributionDateIsNullAndCourierIsNull();

    List<Order> findAllByDistributionDateEqualsAndCourier_IdOrderByGroupId(Date date, Long courierId);

    @Query(value = "select * from orders where distribution_date is null and weight < ?1 and ?2 like '%' || " +
            "region::varchar(255) || '%' order by region", nativeQuery = true)
    List<Order> findAllForDistribute(Float weight, String regions);
}
