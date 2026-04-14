package com.pismo.transaction.service;

import com.pismo.transaction.dto.mapper.EntityMapper;
import com.pismo.transaction.dto.request.TransactionRequestDTO;
import com.pismo.transaction.dto.response.TransactionResponseDTO;
import com.pismo.transaction.exception.OperationTypeNotFoundException;
import com.pismo.transaction.exception.TransactionNotFoundException;
import com.pismo.transaction.model.Account;
import com.pismo.transaction.model.OperationType;
import com.pismo.transaction.model.Transaction;
import com.pismo.transaction.repository.OperationTypeRepository;
import com.pismo.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private OperationTypeRepository operationTypeRepository;

    @Mock
    private EntityMapper mapper;

    @InjectMocks
    private TransactionService transactionService;

    private TransactionRequestDTO requestDTO;
    private Account account;
    private OperationType operationType;
    private Transaction transaction;
    private TransactionResponseDTO transactionResponseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new TransactionRequestDTO(1L, 1L, BigDecimal.valueOf(100));

        account = new Account();
        account.setId(1L);
        account.setDocumentNumber("123456789");

        operationType = new OperationType(1L, "PAYMENT", false);

        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAccount(account);
        transaction.setOperationType(operationType);
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setEventDate(LocalDateTime.now());

        transactionResponseDTO = TransactionResponseDTO.builder()
                .accountId(1L)
                .operationTypeId(1L)
                .amount(BigDecimal.valueOf(100))
                .build();
    }

    @Test
    void testCreateTransactionSuccess() {
        when(accountService.findAccountEntityById(1L)).thenReturn(account);
        when(operationTypeRepository.findById(1L)).thenReturn(Optional.of(operationType));
        when(mapper.toEntity(requestDTO, account, operationType)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(mapper.toResponseDTO(transaction)).thenReturn(transactionResponseDTO);

        var result = transactionService.createTransaction(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getAccountId()).isEqualTo(1L);
        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(100));
        verify(transactionRepository).save(transaction);
    }

    @Test
    void testCreateTransactionInvalidAmount() {
        requestDTO.setAmount(BigDecimal.ZERO);
        when(accountService.findAccountEntityById(1L)).thenReturn(account);
        when(operationTypeRepository.findById(1L)).thenReturn(Optional.of(operationType));

        assertThatThrownBy(() -> transactionService.createTransaction(requestDTO))
                .isInstanceOf(IllegalArgumentException.class);

        verify(transactionRepository, never()).save(any());
    }

    @Test
    void testCreateTransactionOperationTypeNotFound() {
        when(accountService.findAccountEntityById(1L)).thenReturn(account);
        when(operationTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.createTransaction(requestDTO))
                .isInstanceOf(OperationTypeNotFoundException.class);

        verify(transactionRepository, never()).save(any());
    }

    @Test
    void testGetTransactionByIdSuccess() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(mapper.toResponseDTO(transaction)).thenReturn(transactionResponseDTO);

        var result = transactionService.getTransactionById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getOperationTypeId()).isEqualTo(1L);
        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(100));
    }

    @Test
    void testGetTransactionByIdNotFound() {
        when(transactionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.getTransactionById(999L))
                .isInstanceOf(TransactionNotFoundException.class);
    }
}
