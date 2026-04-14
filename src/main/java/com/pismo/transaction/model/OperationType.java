package com.pismo.transaction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "operation_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationType {

    @Id
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String description;

    @Column(name = "negative", nullable = false)
    private Boolean isNegative;
}