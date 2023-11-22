package com.odde.budget;

import java.time.LocalDate;
import java.time.Period;
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
        long result = 0;

        List<YearMonth> periodMonth = getPeriodMonth(start, end);
        for (YearMonth currentYearMonth : periodMonth) {
            Budget budget = getBudget(currentYearMonth);
            result += budget.getAmount();
        }

        if (YearMonth.from(start).equals(YearMonth.from(end))) {
            result = getBudgetInSingleMonth(start, end);
        } else {
            result += getBudgetInSingleMonth(start, YearMonth.from(start).atEndOfMonth());

            result += getBudgetInSingleMonth(YearMonth.from(end).atDay(1), end);
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

    private long getBudgetInSingleMonth(LocalDate start, LocalDate end) {
        int budget = getBudget(YearMonth.from(start)).getBudgetPerDay();
        return (long) budget * (Period.between(start, end).getDays() + 1);
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
}
