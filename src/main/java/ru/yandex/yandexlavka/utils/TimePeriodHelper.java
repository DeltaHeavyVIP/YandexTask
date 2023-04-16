package ru.yandex.yandexlavka.utils;

import org.springframework.stereotype.Component;
import ru.yandex.yandexlavka.exception.InvalidInputDataException;
import ru.yandex.yandexlavka.model.TimePeriod;
import ru.yandex.yandexlavka.repository.TimePeriodRepo;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static java.util.logging.Level.WARNING;

@Component
public class TimePeriodHelper {

    private final TimePeriodRepo timePeriodRepo;

    private String startTimeEndTimeSplitter = "-";

    private String HourMinuteSplitter = ":";

    public TimePeriodHelper(TimePeriodRepo timePeriodRepo) {
        this.timePeriodRepo = timePeriodRepo;
    }

    public Set<TimePeriod> getTimePeriodFromRequest(List<String> timeList) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Set<TimePeriod> timePeriods = new HashSet<>();

        for (String time : timeList) {
            String[] startTimeEndTime = time.split(startTimeEndTimeSplitter);
            Time startTime = null;
            Time endTime = null;
            try {
                validateTimePeriodFromRequest(startTimeEndTime);
                startTime = new Time(formatter.parse(startTimeEndTime[0]).getTime());
                endTime = new Time(formatter.parse(startTimeEndTime[1]).getTime());
            } catch (ParseException e) {
                Logger.getGlobal().log(WARNING, "\n!! Problem with parsing time !!\n Time list information: {" + timeList.toString() + "}");
                throw new InvalidInputDataException();
            }

            /* При использовании Optional<TimePeriod> в TimePeriodRepo и в дальнейшем orElse для элемента создается новый объект, поэтому написал так */
            TimePeriod timePeriod = timePeriodRepo.findFirstByStartTimeEqualsAndEndTimeEquals(startTime, endTime);
            if (timePeriod == null) {
                timePeriod = timePeriodRepo.saveAndFlush(new TimePeriod(startTime, endTime));
            }
            timePeriods.add(timePeriod);
        }
        return timePeriods;
    }

    private void validateTimePeriodFromRequest(String[] startTimeEndTime) {
        String[] startHourMinute = startTimeEndTime[0].split(HourMinuteSplitter);
        String[] endHourMinute = startTimeEndTime[1].split(HourMinuteSplitter);
        if (Integer.parseInt(startHourMinute[0]) >= 24 || Integer.parseInt(startHourMinute[0]) < 0 ||
                Integer.parseInt(endHourMinute[0]) >= 24 || Integer.parseInt(endHourMinute[0]) < 0 ||
                Integer.parseInt(startHourMinute[1]) >= 60 || Integer.parseInt(startHourMinute[1]) < 0 ||
                Integer.parseInt(endHourMinute[1]) >= 60 || Integer.parseInt(endHourMinute[1]) < 0
        ) {
            throw new InvalidInputDataException();
        }
    }

    public List<String> getTimePeriodForResponse(Set<TimePeriod> timePeriods) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        List<String> deliveryHours = new ArrayList<>();
        for (TimePeriod deliveryTime : timePeriods) {
            deliveryHours.add(formatter.format(deliveryTime.getStartTime()) + startTimeEndTimeSplitter + formatter.format(deliveryTime.getEndTime()));
        }
        return deliveryHours;
    }
}
