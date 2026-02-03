package com.javaguides.banking_app.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaguides.banking_app.Exception.AccountException;
import com.javaguides.banking_app.dto.AccountDto;
import com.javaguides.banking_app.dto.TransactionDto;
import com.javaguides.banking_app.dto.TransferFundDTO;
import com.javaguides.banking_app.entity.Account;
import com.javaguides.banking_app.entity.Transaction;
import com.javaguides.banking_app.mapper.AccountMapper;
import com.javaguides.banking_app.repository.AccountRepository;
import com.javaguides.banking_app.repository.TransactionRepository;
import com.javaguides.banking_app.service.AccountService;
@Service
public class AccountServiceimpl implements AccountService {
	
	private AccountRepository accountRepository;
	private TransactionRepository transactionRepository;
	
	
	private static final String TRANSACTION_TYPE_DEPOSIT ="DEPOSIT";
	private static final String TRANSACTION_TYPE_WITHDRAWL ="WITHDRAWL";
	private static final String TRANSACTION_TYPE_TRANSFER ="TRANSFER";
	
	
	@Autowired
	public AccountServiceimpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
		this.accountRepository =accountRepository;
		
		this.transactionRepository=transactionRepository;
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
			   .orElseThrow(() -> new AccountException("Account does not exists"));
	   
		return AccountMapper.maptoAccountDto(account);
	}

	@Override
	public AccountDto deposit(long id, Double amount) {

	    Account account = accountRepository
	            .findById(id)
	            .orElseThrow(() -> new AccountException("Account does not exists"));

	    double total = account.getBalance() + amount;
	    account.setBalance(total);
	    Account savedAccount = accountRepository.save(account);

	    // --- LOGGING THE TRANSACTION ---
	    Transaction transaction = new Transaction();
	    transaction.setAccountId(id);
	    transaction.setAmount(amount); // <--- You forgot this line too!
	    transaction.setTransactionType(TRANSACTION_TYPE_DEPOSIT);
	    transaction.setTimestamp(LocalDateTime.now());

	    transactionRepository.save(transaction); // <--- THIS WAS MISSING
	    
	    return AccountMapper.maptoAccountDto(savedAccount);
	}
	@Override
	public AccountDto withdraw(long id, Double amount) {
	    Account account = accountRepository
	            .findById(id)
	            .orElseThrow(() -> new AccountException("Account does not exists"));

	    if(account.getBalance() < amount) {
	        throw new RuntimeException("Insufficient balance");
	    }

	    double total = account.getBalance() - amount;
	    account.setBalance(total);
	    Account savedAccount = accountRepository.save(account);

	    // --- LOGGING THE TRANSACTION ---
	    Transaction transaction = new Transaction();
	    transaction.setAccountId(id);
	    transaction.setAmount(amount);
	    transaction.setTransactionType(TRANSACTION_TYPE_WITHDRAWL);
	    transaction.setTimestamp(LocalDateTime.now());
	    
	    transactionRepository.save(transaction); // <--- THIS WAS MISSING

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
				   .orElseThrow(() -> new AccountException("Account does not exists"));
		
		accountRepository.deleteById(id);
	}

	@Override
	public void transferFunds(TransferFundDTO transferFundDto) {
		
		Account fromAccount = accountRepository.findById(transferFundDto.fromAccountId()).orElseThrow(() -> new AccountException("account Does not exists"));
		
		Account toAccount = accountRepository.findById(transferFundDto.toAccountId()).orElseThrow(() -> new AccountException("account Does not exists"));
		
		
		if(fromAccount.getBalance() < transferFundDto.amount()) {
			throw new RuntimeException("Insufficient Amount");
		}
		
		fromAccount.setBalance(fromAccount.getBalance() -  transferFundDto.amount());
		toAccount.setBalance(toAccount.getBalance() +  transferFundDto.amount());
		
		accountRepository.save(fromAccount);
		
		accountRepository.save(toAccount);
		
		
		
		
		
		Transaction transaction = new Transaction();
		transaction.setAccountId(transferFundDto.fromAccountId());
		transaction.setAmount(transferFundDto.amount());
		transaction.setTransactionType(TRANSACTION_TYPE_TRANSFER );
		transaction.setTimestamp(LocalDateTime.now());
		
		
		transactionRepository.save(transaction);
	}




	@Override
	public List<TransactionDto> getAccountTransactions(Long AccountId) {
	
		List<Transaction> transactions = transactionRepository.findByAccountIdOrderByTimestampDesc(AccountId);
		
		return transactions.stream().map((transaction) ->  convertEntityToDto(transaction))
		.collect(Collectors.toList());
		

	} 
	
	
	
	private TransactionDto convertEntityToDto(Transaction transaction) {
		
		return new TransactionDto(
		transaction.getId(),
		transaction.getAccountId(),
		transaction.getTransactionType(),
		transaction.getTimestamp());
		
	}
}
