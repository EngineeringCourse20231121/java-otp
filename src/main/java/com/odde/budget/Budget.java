package com.odde.budget;

import java.time.LocalDate;
import java.time.YearMonth;

public class Budget {
    private final YearMonth yearMonth;
    private final int amount;

    public Budget(YearMonth yearMonth, int amount) {
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    public LocalDate getStart() {
        return yearMonth.atDay(1);
    }

    public LocalDate getEnd() {
        return yearMonth.atEndOfMonth();
    }

    public int getDailyAmount() {
        return amount / yearMonth.lengthOfMonth();
    }
}
