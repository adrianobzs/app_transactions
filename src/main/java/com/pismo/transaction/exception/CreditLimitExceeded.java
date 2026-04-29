package com.pismo.transaction.exception;

public class CreditLimitExceeded extends BusinessException {
    public CreditLimitExceeded(String message){
        super(message);
    }
}
