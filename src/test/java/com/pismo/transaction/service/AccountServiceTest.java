package com.pismo.transaction.service;

import com.pismo.transaction.dto.mapper.EntityMapper;
import com.pismo.transaction.dto.request.AccountRequestDTO;
import com.pismo.transaction.dto.response.AccountResponseDTO;
import com.pismo.transaction.exception.DupllicateAccountException;
import com.pismo.transaction.model.Account;
import com.pismo.transaction.repository.AccountRepository;
import com.pismo.transaction.exception.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock private EntityMapper mapper;
    @InjectMocks
    private AccountService accountService;

    private Account account;
    private AccountRequestDTO requestDTO;
    private AccountResponseDTO accountResponseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new AccountRequestDTO();
        requestDTO.setDocumentNumber("123456789");

        account = new Account();
        account.setId(1L);
        account.setDocumentNumber("123456789");

        accountResponseDTO = AccountResponseDTO.builder()
                .documentNumber("123456789")
                .build();
    }

    @Test
    void testCreateAccountSuccess() {
        when(accountRepository.findByDocumentNumber(anyString())).thenReturn(Optional.empty());
        when(mapper.toEntity(any())).thenReturn(account);
        when(accountRepository.save(any())).thenReturn(account);
        when(mapper.toResponseDTO(any(Account.class))).thenReturn(accountResponseDTO);

        var result = accountService.createAccount(requestDTO);

        assertThat(result).isNotNull();
        verify(accountRepository).save(any());
    }

    @Test
    void testCreateAccountDuplicate() {
        when(accountRepository.findByDocumentNumber(anyString())).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> accountService.createAccount(requestDTO))
                .isInstanceOf(DupllicateAccountException.class);

        verify(accountRepository, never()).save(any());
    }

    @Test
    void testGetAccountByIdSuccess() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(mapper.toResponseDTO(any(Account.class))).thenReturn(accountResponseDTO);

        var result = accountService.getAccountById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getDocumentNumber()).isEqualTo("123456789");
    }

    @Test
    void testGetAccountByIdNotFound() {
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.getAccountById(999L))
                .isInstanceOf(AccountNotFoundException.class);
    }
}