package com.odde.budget;

import java.time.LocalDate;
import java.time.YearMonth;

public class Budget {

    private YearMonth yearMonth;
    private int amount;

    public Budget(YearMonth yearMonth, int amount) {
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    public long getOverlappingAmount(TimePeriod timePeriod) {
        return (long) getBudgetPerDay() * timePeriod.getOverlappingDayCount(getPeriod());
    }

    private int getBudgetPerDay() {
        return amount / yearMonth.getMonth().length(yearMonth.isLeapYear());
    }

    private LocalDate getEnd() {
        return yearMonth.atEndOfMonth();
    }

    private TimePeriod getPeriod() {
        return new TimePeriod(getStart(), getEnd());
    }

    private LocalDate getStart() {
        return yearMonth.atDay(1);
    }
}
