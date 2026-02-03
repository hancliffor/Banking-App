package com.javaguides.banking_app.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions") // explicit table name
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Changed from 'long' to 'Long' for consistency

    private Long accountId;

    private double amount;

    // Fixed naming convention (camelCase)
    private String transactionType; // Was 'Transactiontype'

    private LocalDateTime timestamp;
}