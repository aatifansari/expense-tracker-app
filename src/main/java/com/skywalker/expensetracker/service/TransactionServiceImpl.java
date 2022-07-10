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
		
		return transactionRepository.findAll(userId, categoryId);
	}

	@Override
	public Transaction fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId)
			throws EtResourceNotFoundException {
		
		return transactionRepository.findById(userId, categoryId, transactionId);
	}

	@Override
	public Transaction addTransaction(Integer userId, Integer categoryId, Double amount, String note,
			Long transactionDate) throws EtBadRequestException {
		int transactionId = transactionRepository.create(userId, categoryId, amount, note, transactionDate);
		return transactionRepository.findById(userId, categoryId, transactionId);
	}

	@Override
	public Transaction updateTransaction(Integer userId, Integer categoryId, Integer tranactionId, Transaction transaction)
			throws EtBadRequestException {
		transactionRepository.update(userId, categoryId, tranactionId, transaction);
		return transactionRepository.findById(userId, categoryId, tranactionId);
	}

	@Override
	public void removeTransaction(Integer userId, Integer categoryId, Integer transactionId)
			throws EtResourceNotFoundException {
		
		transactionRepository.removeById(userId, categoryId, transactionId);

	}

}
