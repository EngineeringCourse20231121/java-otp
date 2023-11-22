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

        if (YearMonth.from(start).equals(YearMonth.from(end))) {
            return getBudgetInSingleMonth(start, end);
        }

        long result = 0;

        result += getBudgetInSingleMonth(start, YearMonth.from(start).atEndOfMonth());

        for (Budget budget : getBudgets(start, end)) {
            result += budget.getAmount();
        }

        result += getBudgetInSingleMonth(YearMonth.from(end).atDay(1), end);

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

    private List<Budget> getBudgets(LocalDate start, LocalDate end) {
        List<Budget> budgets = new ArrayList<>();
        YearMonth current = YearMonth.from(start).plusMonths(1);
        while (current.isBefore(YearMonth.from(end))) {
            budgets.add(getBudget(current));
            current = current.plusMonths(1);
        }
        return budgets;
    }
}
