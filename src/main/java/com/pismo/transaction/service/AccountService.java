package com.pismo.transaction.service;

import com.pismo.transaction.dto.request.AccountRequestDTO;
import com.pismo.transaction.dto.response.AccountResponseDTO;
import com.pismo.transaction.dto.mapper.EntityMapper;
import com.pismo.transaction.dto.response.AccountResponseDetailDTO;
import com.pismo.transaction.model.Account;
import com.pismo.transaction.repository.AccountRepository;
import com.pismo.transaction.exception.AccountNotFoundException;
import com.pismo.transaction.exception.DupllicateAccountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final EntityMapper mapper;

    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO requestDTO) {
        log.info("Creating account with document: {}", requestDTO.getDocumentNumber());

        accountRepository.findByDocumentNumber(requestDTO.getDocumentNumber())
                .ifPresent(account -> {
                    log.warn("Attempt to create duplicate account with document: {}", requestDTO.getDocumentNumber());
                    throw new DupllicateAccountException(requestDTO.getDocumentNumber());
                });

        Account account = mapper.toEntity(requestDTO);
        Account savedAccount = accountRepository.save(account);

        log.info("Account created successfully with id: {}", savedAccount.getId());
        return mapper.toResponseDTO(savedAccount);
    }

    @Transactional(readOnly = true)
    public AccountResponseDetailDTO getAccountById(Long accountId) {
        log.info("Fetching account with id: {}", accountId);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.error("Account not found with id: {}", accountId);
                    return new AccountNotFoundException(accountId);
                });

        return mapper.toResponseDetailedDTO(account);
    }

    @Transactional(readOnly = true)
    public Account findAccountEntityById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}