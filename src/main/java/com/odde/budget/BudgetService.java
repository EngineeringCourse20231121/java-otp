package com.odde.budget;

import java.time.LocalDate;
import java.time.Period;

public class BudgetService {

    private final BudgetRepo budgetRepo;

    public BudgetService(BudgetRepo budgetRepo) {
        this.budgetRepo = budgetRepo;
    }

    public long queryBudget(LocalDate start, LocalDate end) {
        return budgetRepo.findAll().stream()
                .mapToInt(budget -> getOverlappingDayCount(start, end, budget))
                .sum();
    }

    private int getOverlappingDayCount(LocalDate start, LocalDate end, Budget firstBudget) {
        if (end.isBefore(firstBudget.getStart()) || start.isAfter(firstBudget.getEnd())) {
            return 0;
        }
        LocalDate overlappingStart = start.isAfter(firstBudget.getStart()) ? start : firstBudget.getStart();
        LocalDate overlappingEnd = end.isBefore(firstBudget.getEnd()) ? end : firstBudget.getEnd();
        return Period.between(overlappingStart, overlappingEnd).getDays() + 1;
    }
}
