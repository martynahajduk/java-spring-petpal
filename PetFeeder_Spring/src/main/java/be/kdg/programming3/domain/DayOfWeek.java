package be.kdg.programming3.domain;

public enum DayOfWeek {
    MONDAY("Monday",86400),
    TUESDAY("Tuesday",172800),
    WEDNESDAY("Wednesday",259200),
    THURSDAY("Thursday",345600),
    FRIDAY("Friday",432000),
    SATURDAY("Saturday",518400),
    SUNDAY("Sunday",604800);

    public final String displayName;
    public final int seconds;

    DayOfWeek(String displayName, int seconds) {
        this.displayName = displayName;
        this.seconds = seconds;
    }
}
