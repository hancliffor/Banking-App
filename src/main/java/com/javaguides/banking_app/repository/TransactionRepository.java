package com.javaguides.banking_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.javaguides.banking_app.entity.Transaction;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // CORRECT: usage of CamelCase is strict here
	// This works because the fields are named 'accountId' and 'timestamp'
	List<Transaction> findByAccountIdOrderByTimestampDesc(Long accountId);

}