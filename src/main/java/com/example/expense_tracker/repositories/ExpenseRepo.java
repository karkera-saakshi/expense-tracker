package com.example.expense_tracker.repositories;

import com.example.expense_tracker.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Integer> {
    List<Expense> findByBudgetUserId(int userId);
@Query("SELECT MONTH(e.createdAt), YEAR(e.createdAt), SUM(e.expenseAmount) " +
        "FROM Expense e WHERE e.budget.user.id = :userId " +
        "GROUP BY YEAR(e.createdAt), MONTH(e.createdAt) " +
        "ORDER BY YEAR(e.createdAt), MONTH(e.createdAt)")
List<Object[]> getMonthlyExpenseTotals(@Param("userId") int userId);

    Expense findByUserId(int id);

    Expense findByUser_Id(int id);

    @Query("SELECT COALESCE(SUM(e.expenseAmount), 0) FROM Expense e WHERE e.user.id = :userId")
    double getTotalExpenseByUser(@Param("userId") int userId);

}
