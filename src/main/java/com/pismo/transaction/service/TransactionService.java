package com.pismo.transaction.service;

import com.pismo.transaction.dto.request.TransactionRequestDTO;
import com.pismo.transaction.dto.response.TransactionResponseDTO;
import com.pismo.transaction.dto.mapper.EntityMapper;
import com.pismo.transaction.exception.TransactionNotFoundException;
import com.pismo.transaction.model.Account;
import com.pismo.transaction.model.OperationType;
import com.pismo.transaction.model.Transaction;
import com.pismo.transaction.repository.TransactionRepository;
import com.pismo.transaction.repository.OperationTypeRepository;
import com.pismo.transaction.exception.OperationTypeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final OperationTypeRepository operationTypeRepository;
    private final EntityMapper mapper;

    @Transactional
    public TransactionResponseDTO createTransaction(TransactionRequestDTO requestDTO) {
        log.info("Creating transaction for accountId: {}, operationTypeId: {}, amount: {}",
                requestDTO.getAccountId(), requestDTO.getOperationTypeId(), requestDTO.getAmount());

        Account account = accountService.findAccountEntityById(requestDTO.getAccountId());

        OperationType operationType = operationTypeRepository.findById(requestDTO.getOperationTypeId())
                .orElseThrow(() -> {
                    log.error("Operation type not found with id: {}", requestDTO.getOperationTypeId());
                    return new OperationTypeNotFoundException(requestDTO.getOperationTypeId());
                });

        if (requestDTO.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            log.error("Invalid amount: {} - amount must be positive", requestDTO.getAmount());
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        Transaction transaction = mapper.toEntity(requestDTO, account, operationType);

        Transaction savedTransaction = transactionRepository.save(transaction);

        log.info("Transaction created successfully with id: {}, final amount: {}",
                savedTransaction.getId(), savedTransaction.getAmount());

        return mapper.toResponseDTO(savedTransaction);
    }

    @Transactional(readOnly = true)
    public TransactionResponseDTO getTransactionById(UUID transactionId) {
        log.info("Fetching transaction with id: {}", transactionId);

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> {
                    log.error("Transaction not found with id: {}", transactionId);
                    return new TransactionNotFoundException("Transaction not found with id: " + transactionId);
                });

        return mapper.toResponseDTO(transaction);
    }
}