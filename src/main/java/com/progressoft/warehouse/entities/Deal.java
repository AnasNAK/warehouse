package com.progressoft.warehouse.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="deals" ,uniqueConstraints = @UniqueConstraint(columnNames = "id"))

public class Deal {

    @Id
    private String id;

    @Column(nullable = false ,length = 3)
    private String fromCurrency;

    @Column(nullable = false ,length = 3)
    private String toCurrency;

    @Column(nullable = false ,precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false)
    private Instant timestamp;

}
