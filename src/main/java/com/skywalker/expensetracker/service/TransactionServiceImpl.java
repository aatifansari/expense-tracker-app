package com.skywalker.expensetracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skywalker.expensetracker.domain.Transaction;
import com.skywalker.expensetracker.exceptions.EtBadRequestException;
import com.skywalker.expensetracker.exceptions.EtResourceNotFoundException;
import com.skywalker.expensetracker.repositories.TransactionRepository;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;
	
	@Override
	public List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId) {
		
		return null;
	}

	@Override
	public Transaction fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId)
			throws EtResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note,
			Long transactionDate) throws EtBadRequestException {
		int transactionId = transactionRepository.create(userId, categoryId, amount, note, transactionDate);
		return transactionRepository.findById(userId, categoryId, transactionId);
	}

	@Override
	public void updateTransaction(Integer userId, Integer categoryId, Integer tranactionId, Transaction transaction)
			throws EtBadRequestException {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void removeTransaction(Integer userId, Integer categoryId, Integer transactionId)
			throws EtResourceNotFoundException {
		// TODO Auto-generated method stub

	}

}