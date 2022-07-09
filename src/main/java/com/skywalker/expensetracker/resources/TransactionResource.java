package com.skywalker.expensetracker.resources;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skywalker.expensetracker.domain.Transaction;
import com.skywalker.expensetracker.service.TransactionService;

@RestController
@RequestMapping("/api/{categories}/{categoryId}/transactions")
public class TransactionResource {
	
	@Autowired
	TransactionService transactionService;
	
	@PostMapping("")
	public ResponseEntity<Transaction> addTransaction(HttpServletRequest request,
			              @PathVariable("categoryId") Integer category_Id,
			              @RequestBody Map<String, Object> transactionMap){
		
		int userId = (Integer) request.getAttribute("userId");
		Double amount = Double.valueOf(transactionMap.get("amount").toString());
		String note = (String) transactionMap.get("note");
		Long transactionDate = (Long) transactionMap.get("transactionDate");
		
		Transaction transaction = transactionService.addTransaction(userId, category_Id, amount, note, transactionDate);
		
		return new ResponseEntity<>(transaction, HttpStatus.CREATED);
		
	}

}
