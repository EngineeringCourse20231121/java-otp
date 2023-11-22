package com.odde.budget;

import java.time.LocalDate;
import java.time.Period;

public class TimePeriod {
    private final LocalDate start;
    private final LocalDate end;

    public TimePeriod(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public int getOverlappingDayCount(TimePeriod budgetTimePeriod) {
        LocalDate overlappingStart = start.isAfter(budgetTimePeriod.start) ? start : budgetTimePeriod.start;
        LocalDate overlappingEnd = end.isBefore(budgetTimePeriod.end) ? end : budgetTimePeriod.end;
        if (overlappingStart.isAfter(overlappingEnd)) {
            return 0;
        }
        return new TimePeriod(overlappingStart, overlappingEnd).getDayCount();
    }

    private int getDayCount() {
        return Period.between(start, end).getDays() + 1;
    }
}
