package com.example.expense_tracker.controller;

import com.example.expense_tracker.models.Budget;
import com.example.expense_tracker.models.Expense;
import com.example.expense_tracker.models.Saving;
import com.example.expense_tracker.repositories.BudgetRepo;
import com.example.expense_tracker.repositories.ExpenseRepo;
import com.example.expense_tracker.repositories.SavingRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

@RestController
@CrossOrigin(origins = "*")
public class SavingController {
    @Autowired
    BudgetRepo budgetRepo;
    @Autowired
    SavingRepo savingRepo;
    @Autowired
    ExpenseRepo expenseRepo;
    @GetMapping("/saving/{id}")
    public Saving saving(@PathVariable int id)
    {
        double saving1;
        Saving saving = new Saving();
        Budget budget = budgetRepo.findByUserId(id);
        double totalExpense = expenseRepo.getTotalExpenseByUser(id);
        saving1 = budget.getOriginalBudget() - totalExpense;
        LocalDate now = LocalDate.now();
        saving.setMonth(now.getMonthValue());
        saving.setYear(now.getYear());
        saving.setUser(budget.getUser());
        saving.setSaving(saving1);
        savingRepo.save(saving);
        return saving;
    }
}
