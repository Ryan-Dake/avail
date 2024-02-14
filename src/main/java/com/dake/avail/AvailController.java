package com.dake.avail;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AvailController {

    private GoogleCalenderHelper googleCalenderHelper;

    public AvailController(GoogleCalenderHelper googleCalenderHelper) {
        this.googleCalenderHelper = googleCalenderHelper;
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return googleCalenderHelper.getAvailability();
    }

}
