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
public class Budget {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    int budget_id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;
    @Column(nullable = false)
    double monthlyBudget;
    @Column(nullable = false)
    double originalBudget;
    @CreationTimestamp
    @Column(nullable = false)
    LocalDateTime createdAt;
}
