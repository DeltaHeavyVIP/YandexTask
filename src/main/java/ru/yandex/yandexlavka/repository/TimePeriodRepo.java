package ru.yandex.yandexlavka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.yandexlavka.model.TimePeriod;

import java.sql.Time;

@Repository
public interface TimePeriodRepo extends JpaRepository<TimePeriod, Integer> {

    TimePeriod findFirstByStartTimeEqualsAndEndTimeEquals(Time startTime, Time endTime);

}
