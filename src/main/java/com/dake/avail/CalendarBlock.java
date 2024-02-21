package com.dake.avail;

import java.time.LocalDateTime;

public record CalendarBlock(LocalDateTime time, boolean isAvailable){
    @Override
    public String toString() {
        return "CalendarBlock: hour= " + time + " isAvailable = " + isAvailable + "\n";
    }
}
