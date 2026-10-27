package com.example.clock;

import java.time.ZoneId;

public enum Clock {

    INSTANCE;

    private final ZoneId zoneId = ZoneId.of("Europe/Warsaw");

    public ZoneId zoneId() {
        return zoneId;
    }
}
