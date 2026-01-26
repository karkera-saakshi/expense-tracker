package com.example.expense_tracker.repositories;

import com.example.expense_tracker.models.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepo extends JpaRepository<Budget, Integer> {
    Budget findByUser_Id(int id);


    Budget findByUserId(int id);
}
