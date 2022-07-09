package com.skywalker.expensetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	
	private Integer transactionId;
	private Integer categoryId;
	private Integer userId;
	private Double amount;
	private String note;
	private Long transactionDate;
	
}
