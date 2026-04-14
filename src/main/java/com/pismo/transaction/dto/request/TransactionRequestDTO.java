package com.pismo.transaction.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {

    @NotNull
    private Long accountId;
    @NotNull
    private Long operationTypeId;
    @NotNull
    private BigDecimal amount;
}
