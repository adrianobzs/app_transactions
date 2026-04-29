package com.pismo.transaction.model;

import com.pismo.transaction.exception.CreditLimitExceeded;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @Column(name = "document_number", nullable = false, unique = true, length = 20)
    private String documentNumber;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "available_credit_limit", nullable = false)
    private BigDecimal availableCreditLimit = BigDecimal.ZERO;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    public void updateAvailableCreditLimit(BigDecimal amount){
        if (availableCreditLimit.add(amount).compareTo(BigDecimal.ZERO) < 0){
            throw new CreditLimitExceeded("Credit Limit Exceeded " + availableCreditLimit);
        }
        availableCreditLimit = availableCreditLimit.add(amount);
    }
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}