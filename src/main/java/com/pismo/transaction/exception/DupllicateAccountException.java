package com.pismo.transaction.exception;

public class DupllicateAccountException extends BusinessException {

    public DupllicateAccountException(String documentNumber) {
        super("Account already exists with document number: " + documentNumber);
    }
}
