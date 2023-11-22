package com.odde.budget;

import java.time.LocalDate;
import java.time.YearMonth;

public class Budget {
    private final YearMonth yearMonth;

    public Budget(YearMonth yearMonth, int amount) {
        this.yearMonth = yearMonth;
    }

    public LocalDate getStart() {
        return yearMonth.atDay(1);
    }

    public LocalDate getEnd() {
        return yearMonth.atEndOfMonth();
    }
}
