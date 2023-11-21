package com.odde.budget;

import java.time.YearMonth;

public class Budget {

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

    private YearMonth yearMonth;
    private int amount;
}
