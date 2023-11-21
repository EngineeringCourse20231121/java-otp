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
            result = getBudgetInEndMonth(startYear, startMonthValue, startDayOfMonth, endDayOfMonth);
        } else {
            result += getBudgetInStartMonth(startYear, startMonthValue, startDayOfMonth);

            result += getBudgetInEndMonth(endYear, endMonthValue, 1, endDayOfMonth);
        }

        return result;
    }

    private long getBudgetInStartMonth(int startYear, int startMonthValue, int startDayOfMonth) {
        YearMonth startYearMonth = YearMonth.of(startYear, startMonthValue);
        int startDayBudget = getBudget(startYearMonth).getBudgetPerDay();
        long result = 0L;
        for (int i = startDayOfMonth; i <= startYearMonth.getMonth().length(startYearMonth.isLeapYear()); i++) {
            result += startDayBudget;
        }
        return result;
    }

    private long getBudgetInEndMonth(int endYear, int endMonthValue, int startDay, int endDayOfMonth) {
        YearMonth endYearMonth = YearMonth.of(endYear, endMonthValue);
        int endDayBudget = getBudget(endYearMonth).getBudgetPerDay();
        long result = 0L;
        for (int i = startDay; i <= endDayOfMonth; i++) {
            result += endDayBudget;
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
            current = current.plusMonths(1);
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
