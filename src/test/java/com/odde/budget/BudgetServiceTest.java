package com.odde.budget;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BudgetServiceTest {

    BudgetRepo stubBudgetRepo = mock(BudgetRepo.class);
    BudgetService budgetService = new BudgetService(stubBudgetRepo);

    @Test
    public void no_budget() {
        long total = budgetService.queryBudget(
                LocalDate.of(2023, 11, 2),
                LocalDate.of(2023, 11, 2));

        assertThat(total).isEqualTo(0);
    }

    @Test
    public void one_day_budget() {
        givenBudget(new Budget(YearMonth.of(2023, Month.NOVEMBER), 30));

        long total = budgetService.queryBudget(
                LocalDate.of(2023, 11, 2),
                LocalDate.of(2023, 11, 2));

        assertThat(total).isEqualTo(1);
    }

    @Test
    public void three_day_budget() {
        givenBudget(new Budget(YearMonth.of(2023, Month.NOVEMBER), 30));

        long total = budgetService.queryBudget(
                LocalDate.of(2023, 11, 4),
                LocalDate.of(2023, 11, 6));

        assertThat(total).isEqualTo(3);
    }

    private void givenBudget(Budget budget) {
        when(stubBudgetRepo.findAll()).thenReturn(asList(budget));
    }

}