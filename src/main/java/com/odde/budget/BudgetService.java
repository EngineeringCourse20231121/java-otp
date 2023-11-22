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

        return queryBudgetInTimePeriod(new TimePeriod(start, end));
    }

    private Budget getBudget(YearMonth yearMonth) {
        for (int i = 0; i < budgetList.size(); i++) {
            if (budgetList.get(i).getYearMonth().equals(yearMonth)) {
                return budgetList.get(i);
            }
        }

        return new Budget(yearMonth, 0);
    }

    private long getBudgetInSingleMonth(TimePeriod timePeriod) {
        int budget = getBudget(YearMonth.from(timePeriod.getStart())).getBudgetPerDay();
        return (long) budget * timePeriod.getDayCount();
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

    private long queryBudgetInTimePeriod(TimePeriod timePeriod) {
        if (YearMonth.from(timePeriod.getStart()).equals(YearMonth.from(timePeriod.getEnd()))) {
            return getBudgetInSingleMonth(timePeriod);
        }

        long result = 0;

        result += getBudgetInSingleMonth(new TimePeriod(timePeriod.getStart(), YearMonth.from(timePeriod.getStart()).atEndOfMonth()));

        for (Budget budget : getBudgets(timePeriod.getStart(), timePeriod.getEnd())) {
            result += budget.getAmount();
        }

        result += getBudgetInSingleMonth(new TimePeriod(YearMonth.from(timePeriod.getEnd()).atDay(1), timePeriod.getEnd()));

        return result;
    }
}
