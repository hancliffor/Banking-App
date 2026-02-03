package com.javaguides.banking_app.service;

import java.util.List;

import com.javaguides.banking_app.dto.AccountDto;
import com.javaguides.banking_app.dto.TransactionDto;
import com.javaguides.banking_app.dto.TransferFundDTO;
import com.javaguides.banking_app.entity.Account;

public interface AccountService {

	
	AccountDto createAccount(AccountDto accountDto);
	
	AccountDto getAccountById(Long id);
	
	AccountDto deposit(long id,Double amount);
	
	AccountDto withdraw(long id,Double amount);
	
	List<AccountDto> getAllAccounts();
	
	void deleteAccount(Long id);
	
	void transferFunds(TransferFundDTO transferFundDto);
	
	
	
	List<TransactionDto> getAccountTransactions(Long AccountId);
	
}
