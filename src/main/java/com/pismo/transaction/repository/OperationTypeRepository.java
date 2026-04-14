package com.pismo.transaction.repository;

import com.pismo.transaction.model.Account;
import com.pismo.transaction.model.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {

}
