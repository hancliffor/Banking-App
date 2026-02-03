package com.javaguides.banking_app.dto;

import java.time.LocalDateTime;

public record TransactionDto(
		
		Long accountId,
		 double amount,
		 String transactiontype,
		 LocalDateTime  timestamp) {

}
