package com.dake.avail;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AvailCalendarHelperTests {
    @Test
    void getsTheFirstDayOfWorkWeek() {
        //given
        AvailCalendarHelper availCalendarHelper = new AvailCalendarHelper();
        LocalDate wednesday = LocalDate.of(2024, 2, 21);

        //when
        LocalDate result = availCalendarHelper.getTheCurrentWorkWeekFirstDayOfWeek(wednesday);

        //then
        LocalDate expectedResult = LocalDate.of(2024, 2, 19);
        assertEquals(result, expectedResult);
    }

    @Test
    void getsTheFirstDayOfWorkWeekOnMonday() {
        //given
        AvailCalendarHelper availCalendarHelper = new AvailCalendarHelper();
        LocalDate monday = LocalDate.of(2024, 2, 19);

        //when
        LocalDate result = availCalendarHelper.getTheCurrentWorkWeekFirstDayOfWeek(monday);

        //then
        LocalDate expectedResult = LocalDate.of(2024, 2, 19);
        assertEquals(result, expectedResult);
    }

    @Test
    void getsTheFirstDayOfWorkWeekOnFriday() {
        //given
        AvailCalendarHelper availCalendarHelper = new AvailCalendarHelper();
        LocalDate monday = LocalDate.of(2024, 2, 24);

        //when
        LocalDate result = availCalendarHelper.getTheCurrentWorkWeekFirstDayOfWeek(monday);

        //then
        LocalDate expectedResult = LocalDate.of(2024, 2, 26);
        assertEquals(result, expectedResult);
    }

    @Test
    void getsListOfWorkHoursOnWorkWeek() {
        //given
        AvailCalendarHelper availCalendarHelper = new AvailCalendarHelper();
        LocalDate date = LocalDate.of(2024, 2, 24);

        //when
        List<LocalDateTime> result =
                availCalendarHelper.getListOfWorkHours(date, 8, 17);

        //then
        List<LocalDateTime> expectedResult = List.of(
                LocalDateTime.of(2024, 2, 24, 8, 0),
                LocalDateTime.of(2024, 2, 24, 9, 0),
                LocalDateTime.of(2024, 2, 24, 10, 0),
                LocalDateTime.of(2024, 2, 24, 11, 0),
                LocalDateTime.of(2024, 2, 24, 12, 0),
                LocalDateTime.of(2024, 2, 24, 13, 0),
                LocalDateTime.of(2024, 2, 24, 14, 0),
                LocalDateTime.of(2024, 2, 24, 15, 0),
                LocalDateTime.of(2024, 2, 24, 16, 0),
                LocalDateTime.of(2024, 2, 24, 17, 0));

        assertEquals(expectedResult, result);
    }

    @Test
    void getCurrentWorkWeekReturnsAWeekofAllAvailable() {
        //given
        AvailCalendarHelper availCalendarHelper = new AvailCalendarHelper();

        //when
        Map<DayOfWeek, List<CalendarBlock>> calendar = availCalendarHelper.getNewWorkWeekCalendar(LocalDate.of(2024, 2, 20));

        System.out.println(calendar);

        //then TODO create full result object and properly assert
        assertTrue(calendar.get(DayOfWeek.MONDAY).get(0).isAvailable());
        assertEquals(9, calendar.get(DayOfWeek.MONDAY).size());
    }

    @Test
    void getCurrentWorkWeekReturnsAWeekWithBusy() {
        //given
        AvailCalendarHelper availCalendarHelper = new AvailCalendarHelper();

        //when
        Map<DayOfWeek, List<CalendarBlock>> calendar = availCalendarHelper.getNewWorkWeekCalendar(LocalDate.of(2024, 2, 20));

        ArrayList<LocalDateTime> busyHours = new ArrayList<>();
        busyHours.add(LocalDateTime.of(2024, 2, 20, 11, 0));
        busyHours.add(LocalDateTime.of(2024, 2, 21, 9, 0));
        busyHours.add(LocalDateTime.of(2024, 2, 23, 17, 0));
        calendar = availCalendarHelper.markBusyHours(calendar, busyHours);
        System.out.println("Busy calendar: ");
        System.out.println(calendar);

        boolean tuesdayAvailability = calendar.get(DayOfWeek.TUESDAY).get(2).isAvailable();
        boolean wednesdayAvailability = calendar.get(DayOfWeek.WEDNESDAY).get(0).isAvailable();
        boolean fridayAvailability = calendar.get(DayOfWeek.FRIDAY).get(8).isAvailable();

        //then
        assertTrue(calendar.get(DayOfWeek.MONDAY).get(0).isAvailable());
        assertFalse(tuesdayAvailability);
        assertFalse(wednesdayAvailability);
        assertFalse(fridayAvailability);
    }

}
