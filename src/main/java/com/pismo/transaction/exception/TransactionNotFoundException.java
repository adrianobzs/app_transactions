package com.pismo.transaction.exception;

public class TransactionNotFoundException extends BusinessException {
    public TransactionNotFoundException(Long transactionId) {
        super("Transaction not found with id: " + transactionId);
    }
    public TransactionNotFoundException(String documentNumber) {
        super("Account not found with document number: " + documentNumber);
    }
}
