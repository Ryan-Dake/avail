package com.dake.avail;

import java.time.LocalDateTime;

public record CalendarBlock(LocalDateTime hour, boolean isAvailable){
    @Override
    public String toString() {
        return "CalendarBlock: hour= " + hour + " isAvailable = " + isAvailable;
    }
}
