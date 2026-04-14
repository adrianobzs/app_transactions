package com.pismo.transaction.exception;

public class OperationTypeNotFoundException extends BusinessException {

    public OperationTypeNotFoundException(Long operationTypeId) {
        super("Operation type not found with id: " + operationTypeId);
    }
}