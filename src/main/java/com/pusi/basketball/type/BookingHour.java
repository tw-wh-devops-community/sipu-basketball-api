package com.pusi.basketball.type;

public enum BookingHour {

    HOUR_9(9),
    HOUR_18(18),
    HOUR_20(20),
    HOUR_22(22);

    private final Integer bookingHour;

    BookingHour(final Integer hour) {
        this.bookingHour = hour;
    }

    public Integer hour() {
        return this.bookingHour;
    }
}
