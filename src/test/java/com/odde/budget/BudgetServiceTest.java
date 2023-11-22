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

    @Test
    public void budget_start_with_period_end() {
        givenBudget(new Budget(YearMonth.of(2023, Month.NOVEMBER), 30));

        long total = budgetService.queryBudget(
                LocalDate.of(2023, 10, 25),
                LocalDate.of(2023, 11, 2));

        assertThat(total).isEqualTo(2);
    }

    @Test
    public void period_start_with_budget_end() {
        givenBudget(new Budget(YearMonth.of(2023, Month.NOVEMBER), 30));

        long total = budgetService.queryBudget(
                LocalDate.of(2023, 11, 15),
                LocalDate.of(2023, 12, 10));

        assertThat(total).isEqualTo(16);
    }

    @Test
    public void period_end_is_before_budget_start() {
        givenBudget(new Budget(YearMonth.of(2023, Month.NOVEMBER), 30));

        long total = budgetService.queryBudget(
                LocalDate.of(2023, 10, 24),
                LocalDate.of(2023, 10, 25));

        assertThat(total).isEqualTo(0);
    }

    @Test
    public void period_start_is_after_budget_end() {
        givenBudget(new Budget(YearMonth.of(2023, Month.NOVEMBER), 30));

        long total = budgetService.queryBudget(
                LocalDate.of(2023, 12, 5),
                LocalDate.of(2023, 12, 6));

        assertThat(total).isEqualTo(0);
    }

    @Test
    public void two_budgets() {
        givenBudget(new Budget(YearMonth.of(2023, Month.NOVEMBER), 30),
                new Budget(YearMonth.of(2023, Month.OCTOBER), 31));

        long total = budgetService.queryBudget(
                LocalDate.of(2023, 10, 15),
                LocalDate.of(2023, 11, 7));

        assertThat(total).isEqualTo(17 + 7);
    }

    private void givenBudget(Budget... budgets) {
        when(stubBudgetRepo.findAll()).thenReturn(asList(budgets));
    }

}