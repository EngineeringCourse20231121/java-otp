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

    private List<Budget> getBudgets(LocalDate start, LocalDate end) {
        List<Budget> budgets = new ArrayList<>();
        YearMonth current = YearMonth.from(start);
        while (current.isBefore(YearMonth.from(end).plusMonths(1))) {
            budgets.add(getBudget(current));
            current = current.plusMonths(1);
        }
        return budgets;
    }

    private int getOverlappingDayCount(TimePeriod timePeriod, Budget budget) {
        LocalDate overlappingStart = timePeriod.getStart().isAfter(budget.getStart()) ? timePeriod.getStart() : budget.getStart();
        LocalDate overlappingEnd = timePeriod.getEnd().isBefore(budget.getEnd()) ? timePeriod.getEnd() : budget.getEnd();
        return new TimePeriod(overlappingStart, overlappingEnd).getDayCount();
    }

    private long queryBudgetInTimePeriod(TimePeriod timePeriod) {
        long result = 0;

        for (Budget budget : getBudgets(timePeriod.getStart(), timePeriod.getEnd())) {
            result += (long) budget.getBudgetPerDay() * getOverlappingDayCount(timePeriod, budget);
        }

        return result;
    }

}
