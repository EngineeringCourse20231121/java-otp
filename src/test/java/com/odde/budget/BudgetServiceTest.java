package com.odde.budget;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BudgetServiceTest {

    class MockRepo11 implements BudgetRepo {
        @Override
        public List<Budget> findAll() {
            List<Budget> budgetList = new ArrayList<Budget>();
            budgetList.add(new Budget(YearMonth.of(2022, 11), 30));
            return budgetList;
        }
    }

    class MockRepo11_12 implements BudgetRepo {
        @Override
        public List<Budget> findAll() {
            List<Budget> budgetList = new ArrayList<Budget>();
            budgetList.add(new Budget(YearMonth.of(2022, 11), 30));
            budgetList.add(new Budget(YearMonth.of(2022, 12), 62));
            return budgetList;
        }
    }

    class MockRepo10_12 implements BudgetRepo {
        @Override
        public List<Budget> findAll() {
            List<Budget> budgetList = new ArrayList<Budget>();
            budgetList.add(new Budget(YearMonth.of(2022, 10), 31));
            budgetList.add(new Budget(YearMonth.of(2022, 12), 62));
            return budgetList;
        }
    }

    class MockRepo2022_12_2023_1 implements BudgetRepo {
        @Override
        public List<Budget> findAll() {
            List<Budget> budgetList = new ArrayList<Budget>();
            budgetList.add(new Budget(YearMonth.of(2023, 1), 31));
            budgetList.add(new Budget(YearMonth.of(2022, 12), 62));
            return budgetList;
        }
    }
    @Test
    void test11_5_11_6() {
        BudgetService budgetService = new BudgetService(new MockRepo11());
        long budget = budgetService.queryBudget(LocalDate.of(2022, 11, 5), LocalDate.of(2022, 11, 6));

        assertEquals(2L, budget);
    }

    @Test
    void test10_5_10_6() {
        BudgetService budgetService = new BudgetService(new MockRepo11());
        long budget = budgetService.queryBudget(LocalDate.of(2022, 10, 5), LocalDate.of(2022, 10, 6));

        assertEquals(0L, budget);
    }

    @Test
    void test12_5_12_6() {
        BudgetService budgetService = new BudgetService(new MockRepo11());
        long budget = budgetService.queryBudget(LocalDate.of(2022, 12, 5), LocalDate.of(2022, 12, 6));

        assertEquals(0L, budget);
    }

    @Test
    void test10_31_11_1() {
        BudgetService budgetService = new BudgetService(new MockRepo11());
        long budget = budgetService.queryBudget(LocalDate.of(2022, 10, 31), LocalDate.of(2022, 11, 1));

        assertEquals(1L, budget);
    }

    @Test
    void test11_6_11_5() {
        BudgetService budgetService = new BudgetService(new MockRepo11());
        long budget = budgetService.queryBudget(LocalDate.of(2022, 11, 6), LocalDate.of(2022, 11, 5));

        assertEquals(0L, budget);
    }

    @Test
    void test11_1_11_30() {
        BudgetService budgetService = new BudgetService(new MockRepo11());
        long budget = budgetService.queryBudget(LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 30));

        assertEquals(30L, budget);
    }
    @Test
    void test11_30_12_1_empty12() {
        BudgetService budgetService = new BudgetService(new MockRepo11());
        long budget = budgetService.queryBudget(LocalDate.of(2022, 11, 30), LocalDate.of(2022, 12, 1));

        assertEquals(1L, budget);
    }

    @Test
    void test11_30_12_1() {
        BudgetService budgetService = new BudgetService(new MockRepo11_12());
        long budget = budgetService.queryBudget(LocalDate.of(2022, 11, 30), LocalDate.of(2022, 12, 1));

        assertEquals(3L, budget);
    }
    @Test
    void test11_1_12_31() {
        BudgetService budgetService = new BudgetService(new MockRepo11_12());
        long budget = budgetService.queryBudget(LocalDate.of(2022, 11, 1), LocalDate.of(2022, 12, 31));

        assertEquals(92L, budget);
    }

    @Test
    void test2022_12_31_2023_1_1() {
        BudgetService budgetService = new BudgetService(new MockRepo2022_12_2023_1());
        long budget = budgetService.queryBudget(LocalDate.of(2022, 12, 31), LocalDate.of(2023, 1, 1));

        assertEquals(3L, budget);
    }
    @Test
    void test2022_1_1_2023_1_1() {
        BudgetService budgetService = new BudgetService(new MockRepo2022_12_2023_1());
        long budget = budgetService.queryBudget(LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1));

        assertEquals(63L, budget);
    }

    @Test
    void test2022_10_1_2022_12_1() {
        BudgetService budgetService = new BudgetService(new MockRepo10_12());
        long budget = budgetService.queryBudget(LocalDate.of(2022, 10, 1), LocalDate.of(2022, 12, 1));

        assertEquals(33L, budget);
    }

    @Test
    void test2022_10_1_2022_12_31() {
        BudgetService budgetService = new BudgetService(new MockRepo10_12());
        long budget = budgetService.queryBudget(LocalDate.of(2022, 10, 1), LocalDate.of(2022, 12, 31));

        assertEquals(93L, budget);
    }
}