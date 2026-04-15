package com.pismo.transaction.repository;

import com.pismo.transaction.model.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {

}
