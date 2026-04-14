package com.pismo.transaction.exception;

public class AccountNotFoundException extends BusinessException {
    public AccountNotFoundException(Long accountId) {
        super("Account not found with id: " + accountId);
    }

    public AccountNotFoundException(String documentNumber) {
        super("Account not found with document number: " + documentNumber);
    }
}
