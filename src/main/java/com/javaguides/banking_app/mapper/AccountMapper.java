package com.javaguides.banking_app.mapper;

import com.javaguides.banking_app.dto.AccountDto;
import com.javaguides.banking_app.entity.Account;

public class AccountMapper {

	
	
	public static Account mapToAccount(AccountDto accountDto) {
		
		Account account = new Account(
				
				accountDto.id(),
				accountDto.accountName(),
				accountDto.balance()
				);
	
		return account;
		
	}
	
	
	public static AccountDto maptoAccountDto(Account account) {
		AccountDto accountDto = new AccountDto(
				
				account.getId(),
				account.getAccountName(),
				account.getBalance()
				);
			return accountDto;	
		
	}
}
