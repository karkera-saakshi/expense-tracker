package com.example.expense_tracker.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Saving {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    double saving;
    @Column(nullable = false)
    int month;
    @Column(nullable = false)
    int year;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
