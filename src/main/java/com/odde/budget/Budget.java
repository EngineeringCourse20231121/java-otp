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

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public int getAmount() {
        return amount;
    }

    public int getBudgetPerDay() {
        return amount / yearMonth.getMonth().length(yearMonth.isLeapYear());
    }

    public LocalDate getStart() {
        return yearMonth.atDay(1);
    }

    LocalDate getEnd() {
        return yearMonth.atEndOfMonth();
    }
}
