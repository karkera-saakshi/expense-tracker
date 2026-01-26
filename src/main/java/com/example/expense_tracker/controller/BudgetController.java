package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.BudgetDto;
import com.example.expense_tracker.dto.DisplayDto;
import com.example.expense_tracker.models.Budget;
import com.example.expense_tracker.models.User;
import com.example.expense_tracker.repositories.BudgetRepo;
import com.example.expense_tracker.repositories.ExpenseRepo;
import com.example.expense_tracker.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class BudgetController {
    @Autowired
    BudgetRepo budgetRepo;
    @Autowired
    UserRepo userRepo;

    @PostMapping("/setBudget")
    public String setBudget(@RequestBody BudgetDto budgetDto)
    {
        User user = userRepo.findById(budgetDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        Budget budget = budgetRepo.findByUser_Id(user.getId());

        if (budget == null) {
            budget = new Budget();
            budget.setUser(user);
        }

        budget.setMonthlyBudget(budgetDto.getMonthlyBudget());
        budget.setOriginalBudget(budgetDto.getMonthlyBudget());
        budgetRepo.save(budget);

        return "Budget Updated";
    }

    @GetMapping("/get-details/{id}")
    public DisplayDto display(@PathVariable int id)
    {
//        Budget budget = budgetRepo.findByUserId(id).orElseThrow(()->new RuntimeException("User Not Found"));
        Budget budget = budgetRepo.findByUserId(id);
        DisplayDto displayDto = new DisplayDto();
        displayDto.setNewmonthlyBudget(budget.getMonthlyBudget());
        if (displayDto.getNewmonthlyBudget() <=0)
        {
            displayDto.setNewmonthlyBudget(0);
        }
        return displayDto;
    }
}
