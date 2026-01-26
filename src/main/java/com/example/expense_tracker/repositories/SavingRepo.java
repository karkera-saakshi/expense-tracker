package com.example.expense_tracker.repositories;

import com.example.expense_tracker.models.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingRepo extends JpaRepository<Saving, Integer> {
    @Query("""
    SELECT COALESCE(SUM(e.expenseAmount), 0)
    FROM Expense e
    WHERE e.budget.user.id = :userId
      AND MONTH(e.createdAt) = :month
      AND YEAR(e.createdAt) = :year
""")
    double getTotalExpenseForMonth(
            @Param("userId") int userId,
            @Param("month") int month,
            @Param("year") int year
    );

}
