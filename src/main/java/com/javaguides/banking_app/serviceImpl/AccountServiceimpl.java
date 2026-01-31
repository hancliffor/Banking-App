package com.javaguides.banking_app.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaguides.banking_app.dto.AccountDto;
import com.javaguides.banking_app.entity.Account;
import com.javaguides.banking_app.mapper.AccountMapper;
import com.javaguides.banking_app.repository.AccountRepository;
import com.javaguides.banking_app.service.AccountService;
@Service
public class AccountServiceimpl implements AccountService {
	
	private AccountRepository accountRepository;
	
	@Autowired
	public AccountServiceimpl(AccountRepository accountRepository) {
		this.accountRepository =accountRepository;
	}

	@Override
	public AccountDto createAccount(AccountDto accountDto) {
	    Account account = AccountMapper.mapToAccount(accountDto);
	   Account savedAccount = accountRepository.save(account);
		return AccountMapper.maptoAccountDto(savedAccount);
	}

	@Override
	public AccountDto getAccountById(Long id) {
	   Account account = accountRepository
			   .findById(id)
			   .orElseThrow(() -> new RuntimeException("Account does not exists"));
	   
		return AccountMapper.maptoAccountDto(account);
	}

	@Override
	public AccountDto deposit(long id, Double amount) {
		
		
		   Account account = accountRepository
				   .findById(id)
				   .orElseThrow(() -> new RuntimeException("Account does not exists"));
		   
		   double total = account.getBalance() + amount;
		   account.setBalance(total);
		Account savedAccount = accountRepository.save(account);
		return AccountMapper.maptoAccountDto(savedAccount);
	}

	@Override
	public AccountDto withdraw(long id, Double amount) {
		Account account = accountRepository
				   .findById(id)
				   .orElseThrow(() -> new RuntimeException("Account does not exists"));
		
		if(account.getBalance() < amount) {
			throw new RuntimeException("Insufficient balance");
		}
		   
		   double total = account.getBalance() - amount;
		   account.setBalance(total);
		Account savedAccount = accountRepository.save(account);
		return AccountMapper.maptoAccountDto(savedAccount);
	}

	@Override
	public List<AccountDto> getAllAccounts() {
	    List<Account> accounts = accountRepository.findAll();

	    // You must return the result of the stream or assign it to a variable
	    return accounts.stream()
	                   .map((account) -> AccountMapper.maptoAccountDto(account))
	                   .collect(Collectors.toList());
	}

	@Override
	public void deleteAccount(Long id) {
		Account account = accountRepository
				   .findById(id)
				   .orElseThrow(() -> new RuntimeException("Account does not exists"));
		
		accountRepository.deleteById(id);
	} 
}
