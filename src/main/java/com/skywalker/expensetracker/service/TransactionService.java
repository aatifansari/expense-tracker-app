package com.skywalker.expensetracker.service;

import java.util.List;

import com.skywalker.expensetracker.domain.Transaction;
import com.skywalker.expensetracker.exceptions.EtBadRequestException;
import com.skywalker.expensetracker.exceptions.EtResourceNotFoundException;

public interface TransactionService {
	
	List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId, Integer pageNo, Integer pageSize);
	
	Transaction fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId)
	                   throws EtResourceNotFoundException;
	
	Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note,
			Long transactionDate) throws EtBadRequestException;
	
	Transaction updateTransaction(Integer userId, Integer categoryId, Integer tranactionId, Transaction transaction)
	                 throws EtBadRequestException;
	
	void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException;
	
	List<Transaction> getTransactionByCategoryName(String categoryName, Integer userId) throws EtResourceNotFoundException;
	
}
