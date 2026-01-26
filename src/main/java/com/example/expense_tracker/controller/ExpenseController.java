package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.ExpenseDto;
import com.example.expense_tracker.dto.MonthlyExpenseSummaryDto;
import com.example.expense_tracker.models.Budget;
import com.example.expense_tracker.models.Expense;
import com.example.expense_tracker.repositories.BudgetRepo;
import com.example.expense_tracker.repositories.ExpenseRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseController {

    @Autowired
    BudgetRepo budgetRepo;
    @Autowired
    ExpenseRepo expenseRepo;
    @PostMapping("/expense")
    public String expense(@RequestBody ExpenseDto expenseDto)
    {
        Budget budget = budgetRepo.findByUser_Id(expenseDto.getId());
        if (budget == null) {
            throw new RuntimeException("Budget Not Found");
        }
        double newmonthlyBudget = budget.getMonthlyBudget() - expenseDto.getExpenseAmount();
        budget.setMonthlyBudget(newmonthlyBudget);
        budgetRepo.save(budget);

        Expense obj = new Expense();
        if(expenseDto.getExpenseAmount()==0)
        {
            return "You cannot set amount as 0";
        }
        if(expenseDto.getExpenseAmount() < 0 )
        {
            return "Enter a valid amount";
        }
        if(expenseDto.getExpenseAmount() == null)
        {
            return "Enter amount";
        }
        if (expenseDto.getExpenseAmount() > budget.getMonthlyBudget())
        {
            return "Out of budget";
        }

            obj.setExpenseAmount(expenseDto.getExpenseAmount());
            obj.setCategory(expenseDto.getCategory());
            obj.setDescription(expenseDto.getDescription());
            obj.setMood(expenseDto.getMood());
            obj.setUser(budget.getUser());
            obj.setBudget(budget);
            expenseRepo.save(obj);

            return "Your expense noted";
    }

    @GetMapping("/histories/{userId}")
    public List<Expense> histories(@PathVariable int userId) {
        return expenseRepo.findByBudgetUserId(userId);
    }


    @GetMapping("/monthly-summary/{userId}")
    public List<MonthlyExpenseSummaryDto> getMonthlySummary(@PathVariable int userId) {

        List<Object[]> result = expenseRepo.getMonthlyExpenseTotals(userId);

        return result.stream().map(obj -> {
            int month = (int) obj[0];
            int year = (int) obj[1];
            double total = obj[2] == null ? 0.0 : ((Number) obj[2]).doubleValue();


            String monthName = Month.of(month).name(); // JANUARY
            monthName = monthName.substring(0,1) + monthName.substring(1).toLowerCase();

            return new MonthlyExpenseSummaryDto(
                    monthName + " " + year,
                    total
            );
        }).toList();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id)
    {
        Expense expense = expenseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        Budget budget = expense.getBudget();
        double newValue = expense.getExpenseAmount() + budget.getMonthlyBudget();
        budget.setMonthlyBudget(newValue);
        expenseRepo.deleteById(id);
        budgetRepo.save(budget);
    }

}
