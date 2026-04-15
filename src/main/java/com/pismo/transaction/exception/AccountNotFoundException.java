package com.pismo.transaction.exception;

import java.util.UUID;

public class AccountNotFoundException extends BusinessException {
    public AccountNotFoundException(UUID accountId) {
        super("Account not found with id: " + accountId);
    }

    public AccountNotFoundException(String documentNumber) {
        super("Account not found with document number: " + documentNumber);
    }
}
