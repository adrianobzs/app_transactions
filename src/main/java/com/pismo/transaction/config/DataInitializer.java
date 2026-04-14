package com.pismo.transaction.config;

import com.pismo.transaction.model.OperationType;
import com.pismo.transaction.model.OperationTypeEnum;
import com.pismo.transaction.repository.OperationTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final OperationTypeRepository operationTypeRepository;

    @Override
    public void run(String... args) {
        if (operationTypeRepository.count() == 0) {
            log.info("Setting Operation Types...");

            for (OperationTypeEnum type : OperationTypeEnum.values()) {
                OperationType operationType = new OperationType();
                operationType.setId(type.getId());
                operationType.setDescription(type.getDescription());
                operationType.setIsNegative(type.isNegative());

                operationTypeRepository.save(operationType);
                log.info("Operation Types created: {} - {}", type.getId(), type.getDescription());
            }

            log.info("Operation Types Set successfully!");
        }
    }
}