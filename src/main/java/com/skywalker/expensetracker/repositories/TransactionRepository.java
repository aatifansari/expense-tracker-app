package com.skywalker.expensetracker.repositories;

import java.util.List;

import com.skywalker.expensetracker.domain.Transaction;
import com.skywalker.expensetracker.exceptions.EtBadRequestException;
import com.skywalker.expensetracker.exceptions.EtResourceNotFoundException;

public interface TransactionRepository {
	
	List<Transaction> findAll(Integer userId, Integer categoryId);
	
	List<Transaction> findAllPageable(Integer userId, Integer categoryId, Integer pageNo, Integer pageSize);

	Transaction findById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException;
	
	Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate)
	                   throws EtBadRequestException;

	void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction)
			throws EtBadRequestException;

	void removeById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException;
	
	List<Transaction> findByCategoryName(String categoryName, Integer userId);

}
