package com.pismo.transaction.model;

import lombok.Getter;

//TODO check the need of this enum once we already have the entity OperationType

@Getter
public enum OperationTypeEnum {
    COMPRA_A_VISTA(1L, "COMPRA A VISTA", true),
    COMPRA_PARCELADA(2L, "COMPRA PARCELADA", true),
    SAQUE(3L, "SAQUE", true),
    PAGAMENTO(4L, "PAGAMENTO", false);

    private final Long id;
    private final String description;
    private final boolean isNegative;

    OperationTypeEnum(Long id, String description, boolean isNegative) {
        this.id = id;
        this.description = description;
        this.isNegative = isNegative;
    }
}