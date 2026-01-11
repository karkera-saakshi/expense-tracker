package com.example.expense_tracker.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "budget_id", nullable = false)
    private Budget budget;
    @Column(nullable = false)
    Double expenseAmount;
    @Column(nullable = false)
    String description;
    @Column(nullable = false)
    String category;
    @Column(nullable = false)
    String mood;
    @CreationTimestamp
    LocalDateTime createdAt;
}
