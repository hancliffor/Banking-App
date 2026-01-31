package com.javaguides.banking_app.dto;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//@Data
//@AllArgsConstructor
//public class AccountDto {
//	
//	private Long id;
//	private String accountName;
//	private double balance;
//	
//
//}
public record AccountDto(Long id, String accountName,
									double balance) {
	
}