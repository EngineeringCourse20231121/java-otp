package com.odde.budget;

import java.time.LocalDate;
import java.time.Period;

public class BudgetService {

    private final BudgetRepo budgetRepo;

    public BudgetService(BudgetRepo budgetRepo) {
        this.budgetRepo = budgetRepo;
    }

    public long queryBudget(LocalDate start, LocalDate end) {
        if (budgetRepo.findAll().isEmpty()) {
            return 0;
        }
        Budget firstBudget = budgetRepo.findAll().get(0);
        return getOverlappingDayCount(start, end, firstBudget);
    }

    private int getOverlappingDayCount(LocalDate start, LocalDate end, Budget firstBudget) {
        if (end.isBefore(firstBudget.getStart())) {
            return 0;
        }
        if (firstBudget.getEnd().isBefore(end)) {
            return Period.between(start, firstBudget.getEnd()).getDays() + 1;
        }
        if (firstBudget.getStart().isAfter(start)) {
            return Period.between(firstBudget.getStart(), end).getDays() + 1;
        }
        return Period.between(start, end).getDays() + 1;
    }
}
