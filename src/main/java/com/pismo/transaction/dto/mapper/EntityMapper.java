package com.pismo.transaction.dto.mapper;

import com.pismo.transaction.dto.request.AccountRequestDTO;
import com.pismo.transaction.dto.request.TransactionRequestDTO;
import com.pismo.transaction.dto.response.AccountResponseDTO;
import com.pismo.transaction.dto.response.AccountResponseDetailDTO;
import com.pismo.transaction.dto.response.TransactionResponseDTO;
import com.pismo.transaction.model.Account;
import com.pismo.transaction.model.OperationType;
import com.pismo.transaction.model.Transaction;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class EntityMapper {

    public Account toEntity(AccountRequestDTO requestDTO) {
        Account account = new Account();
        account.setDocumentNumber(requestDTO.getDocumentNumber());
        return account;
    }


    public AccountResponseDTO toResponseDTO(Account account) {
        return AccountResponseDTO.builder()
                .documentNumber(account.getDocumentNumber())
                .build();
    }

    public AccountResponseDetailDTO toResponseDetailedDTO(Account account) {
        return AccountResponseDetailDTO.builder()
                .accountId(account.getId())
                .documentNumber(account.getDocumentNumber())
                .availableCreditLimit(account.getAvailableCreditLimit())
                .build();
    }

    public Transaction toEntity(TransactionRequestDTO requestDTO, Account account, OperationType operationType) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setOperationType(operationType);

        BigDecimal amount = requestDTO.getAmount();
        if (operationType.getIsNegative()) {
            amount = amount.negate();
        }
        transaction.setAmount(amount);

        return transaction;
    }

    public TransactionResponseDTO toResponseDTO(Transaction transaction) {
        return TransactionResponseDTO.builder()
                .accountId(transaction.getAccount().getId())
                .operationTypeId(transaction.getOperationType().getId())
                .amount(transaction.getAmount())
                .build();
    }
}