package com.javaguides.banking_app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaguides.banking_app.dto.AccountDto;
import com.javaguides.banking_app.dto.TransactionDto;
import com.javaguides.banking_app.dto.TransferFundDTO;
import com.javaguides.banking_app.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	
	private AccountService accountService;

	public AccountController(AccountService accountService) {

		this.accountService = accountService;
	}
	
	
	
	@PostMapping
	public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
		return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> getAccountbyId(@PathVariable Long id){
		
		AccountDto accountDto =accountService.getAccountById(id);
		return ResponseEntity.ok(accountDto);
	}
	
	
	@PutMapping("/{id}/deposit")
	public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request){
		
		Double amount=  request.get("amount");
		AccountDto accountDto = accountService.deposit(id,amount);
		return  ResponseEntity.ok(accountDto) ;
		
	}
	
	@PutMapping("/{id}/withdraw")
	public ResponseEntity<AccountDto> withdraw( @PathVariable Long id, @RequestBody Map<String, Double> request ){
		Double amount=  request.get("amount");
		AccountDto accountDto = accountService.withdraw(id,amount);
		return  ResponseEntity.ok(accountDto) ;
		
		
	}
	
	@GetMapping
	public ResponseEntity<List<AccountDto>> getAllAccounts(){
		List<AccountDto> accounts = accountService.getAllAccounts();
		return ResponseEntity.ok(accounts);
		
	}
	
	@DeleteMapping("/{id}")
	public  ResponseEntity<String> deleteAccount(@PathVariable Long id){

		
		accountService.deleteAccount(id);
		
		return ResponseEntity.ok("account deleted successfully");
		
		
	}
	
	
	@PostMapping("/transfer")
	public  ResponseEntity<String>  transferFund(@RequestBody TransferFundDTO tranferFundDto){
		
		accountService.transferFunds(tranferFundDto);
		
		return ResponseEntity.ok("fund transferredsuccessfully");
		
		
	}
	
	@GetMapping("/{id}/transactions")
	public ResponseEntity<List<TransactionDto>> fetchAccountTransactions(@PathVariable("id") Long accountId){
		
		List<TransactionDto> transactions = accountService.getAccountTransactions(accountId);
		
		return ResponseEntity.ok(transactions);
	}
}
