package com.dake.avail;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class CalendarService {
    private static final String APPLICATION_NAME = "Avail";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private final Credential credential;

    public CalendarService(Credential credential) {
        this.credential = credential;
    }

    public FreeBusyCalendar getAvailability() throws IOException, GeneralSecurityException {
        LocalDateTime dateTimeAfter7Days = LocalDateTime.now().plusDays(7);
        long millis = dateTimeAfter7Days.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        DateTime googleDateTimeNow = new DateTime(System.currentTimeMillis());
        DateTime googleDateTimePlus7 = new DateTime(millis);

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service =
                new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                        .setApplicationName(APPLICATION_NAME)
                        .build();

        FreeBusyRequest freeBusyRequest = new FreeBusyRequest();
        freeBusyRequest.setTimeMin(googleDateTimeNow);
        freeBusyRequest.setTimeMax(googleDateTimePlus7);
        freeBusyRequest.setItems(List.of(new FreeBusyRequestItem().setId("primary")));
        FreeBusyResponse response = service.freebusy().query(freeBusyRequest).execute();
        System.out.println("FreeBusy Response: " + response.toString());

        FreeBusyCalendar calendar = response.getCalendars().get("primary");
        System.out.println(calendar);
        return calendar;
    }


}