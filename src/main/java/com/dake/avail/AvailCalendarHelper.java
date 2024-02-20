package com.dake.avail;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvailCalendarHelper {
    public Map<DayOfWeek, List<CalendarBlock>> getNewWorkWeekCalendar(LocalDate currentDate) {
        LocalDate firstDayOfWorkWeek = getTheCurrentWorkWeekFirstDayOfWeek(currentDate);
        System.out.println("Generating calendar for work week starting on " + firstDayOfWorkWeek);

        Map<DayOfWeek, List<CalendarBlock>> availability = new HashMap<>();
        availability.put(DayOfWeek.MONDAY, addCalendarBlocks(firstDayOfWorkWeek));
        availability.put(DayOfWeek.TUESDAY, addCalendarBlocks(firstDayOfWorkWeek.plusDays(1)));
        availability.put(DayOfWeek.WEDNESDAY, addCalendarBlocks(firstDayOfWorkWeek.plusDays(2)));
        availability.put(DayOfWeek.THURSDAY, addCalendarBlocks(firstDayOfWorkWeek.plusDays(3)));
        availability.put(DayOfWeek.FRIDAY, addCalendarBlocks(firstDayOfWorkWeek.plusDays(4)));

        System.out.println("Calendar is:");
        System.out.println(availability);

        return availability;
    }

    public List<CalendarBlock> addCalendarBlocks(LocalDate workDate) {
        List<LocalDateTime> hours = getListOfWorkHours(workDate, 9, 17);
        System.out.println("Working hours" + hours);
        return getNewWorkDay(hours);
    }
    public LocalDate getTheCurrentWorkWeekFirstDayOfWeek(LocalDate currentDate) {
        System.out.println("Transforming date " + currentDate + " to Monday of the current or next business week if it's the weekend...");

        LocalDate nearestMonday = switch (currentDate.getDayOfWeek()) {
            case MONDAY -> currentDate;
            case TUESDAY -> currentDate.minusDays(1);
            case WEDNESDAY -> currentDate.minusDays(2);
            case THURSDAY -> currentDate.minusDays(3);
            case FRIDAY -> currentDate.minusDays(4);
            case SATURDAY -> currentDate.plusDays(2);
            case SUNDAY -> currentDate.plusDays(1);
        };

        System.out.println("Transformed to " + nearestMonday);

        return nearestMonday;
    }

    public List<LocalDateTime> getListOfWorkHours(LocalDate workDay, int startHour, int endHour) {
        List<LocalDateTime> result = new ArrayList<>();
        for(int i = startHour; i <= endHour; i++) {
            result.add(LocalDateTime.of(workDay.getYear(), workDay.getMonthValue(), workDay.getDayOfMonth(), i, 0));
        }
        return result;
    }

    public List<CalendarBlock> getNewWorkDay(List<LocalDateTime> workHours) {
        return workHours.stream().map(localDateTime -> new CalendarBlock(localDateTime, true)).toList();
    }
    
}
