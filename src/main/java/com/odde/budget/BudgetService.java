package com.odde.budget;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class BudgetService {
    List<Budget> budgetList;

    public BudgetService(BudgetRepo budgetRepo) {
        budgetList = budgetRepo.findAll();
    }

    public long queryBudget(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) return 0;
        int startYear = start.getYear();
        int startMonthValue = start.getMonthValue();
        int startDayOfMonth = start.getDayOfMonth();
        int endYear = end.getYear();
        int endMonthValue = end.getMonthValue();
        int endDayOfMonth = end.getDayOfMonth();
        long result = 0;

        List<YearMonth> periodMonth = getPeriodMonth(start, end);
        for (YearMonth currentYearMonth : periodMonth) {
            Budget budget = getBudget(currentYearMonth);
            result += budget.getAmount();
        }
        if (startYear == endYear && startMonthValue == endMonthValue) {
            YearMonth currentYearMonth = YearMonth.of(startYear, startMonthValue);
            int dayBudget = getBudget(currentYearMonth).getBudgetPerDay();
            for (int i = startDayOfMonth; i <= endDayOfMonth; i++) {
                result += dayBudget;
            }
        } else {
            YearMonth startYearMonth = YearMonth.of(startYear, startMonthValue);
            int startDayBudget = getBudget(startYearMonth).getBudgetPerDay();
            for (int i = startDayOfMonth; i <= startYearMonth.getMonth().length(startYearMonth.isLeapYear()); i++) {
                result += startDayBudget;
            }

            YearMonth endYearMonth = YearMonth.of(endYear, endMonthValue);
            int endDayBudget = getBudget(endYearMonth).getBudgetPerDay();
            for (int i = 1; i <= endDayOfMonth; i++) {
                result += endDayBudget;
            }
        }

        return result;
    }

    private List<YearMonth> getPeriodMonth(LocalDate start, LocalDate end) {
        int startYear = start.getYear();
        int startMonthValue = start.getMonthValue();
        int endYear = end.getYear();
        int endMonthValue = end.getMonthValue();

        YearMonth endYearMonth = YearMonth.of(endYear, endMonthValue);

        List<YearMonth> result = new ArrayList<>();
        YearMonth current = YearMonth.of(startYear, startMonthValue).plusMonths(1);
        while (current.isBefore(endYearMonth)) {
            result.add(current);
            startMonthValue++;
            if (startMonthValue > 12) {
                startYear++;
                startMonthValue = 1;
            }
            current = YearMonth.of(startYear, startMonthValue).plusMonths(1);
        }
        return result;
    }

    private Budget getBudget(YearMonth yearMonth) {
        for (int i = 0; i < budgetList.size(); i++) {
            if (budgetList.get(i).getYearMonth().equals(yearMonth)) {
                return budgetList.get(i);
            }
        }

        return new Budget(yearMonth, 0);
    }
}
