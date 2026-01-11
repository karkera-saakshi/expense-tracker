package com.example.expense_tracker.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyExpenseSummaryDto {
    private String month;   // "January 2026"
    private double total;
}

