package com.dake.avail;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class AvailController {

    private final CalendarService calendarService;

    public AvailController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/avail")
    public String avail() throws GeneralSecurityException, IOException {
        return calendarService.getAvailability().toString();
    }

}
