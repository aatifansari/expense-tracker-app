package com.skywalker.expensetracker.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skywalker.expensetracker.domain.Transaction;
import com.skywalker.expensetracker.service.TransactionService;

@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class TransactionResource {
	
	@Autowired
	TransactionService transactionService;
	
	@GetMapping("")
	public ResponseEntity<List<Transaction>> getAllTransaction(HttpServletRequest request,
			                 @PathVariable("categoryId") @Valid Integer categoryId,
			                 @RequestParam(name = "pageNo", required = false) Integer pageNo,
			                 @RequestParam(name = "pageSize", required = false) Integer pageSize){
		                     
		int userId = (Integer) request.getAttribute("userId");
		List<Transaction> transactions = transactionService.fetchAllTransactions(userId, categoryId, pageNo, pageSize);
		
		return new ResponseEntity(transactions, HttpStatus.OK);
	}
	
	@GetMapping("/{transactionId}")
	public ResponseEntity<Transaction> getTransactionById(HttpServletRequest request,
			@PathVariable("categoryId") Integer categoryId,
			@PathVariable("transactionId") Integer transactionId){
		
		int userId = (Integer) request.getAttribute("userId");
		Transaction transaction = transactionService.fetchTransactionById(userId, categoryId, transactionId);
		
		return new ResponseEntity<>(transaction, HttpStatus.OK);
	}
	
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
	
	@PutMapping("")
	public ResponseEntity<Transaction> updateTransaction(HttpServletRequest request,
			                            @PathVariable("categoryId") Integer categoryId,
	                                    @RequestBody Map<String, Object> transactionMap){
	  int userId = (Integer) request.getAttribute("userId");
	  Double amount = Double.valueOf(transactionMap.get("amount").toString());
	  String note = (String) transactionMap.get("note");
	  Long transactionDate = (Long) transactionMap.get("transactionDate");
	  Integer transactionId = (Integer) transactionMap.get("transactionId");
	  
	  Transaction transaction = Transaction.builder()
	  .transactionId(transactionId)
	  .amount(amount)
	  .note(note)
	  .transactionDate(transactionDate)
	  .build();
	  
	  Transaction updatedtransaction = transactionService.updateTransaction(userId, categoryId, transactionId, transaction);   
	  
	  return new ResponseEntity<>(updatedtransaction, HttpStatus.OK);
	}
	
	@DeleteMapping("/{transactionId}")
	public ResponseEntity<Map<String, Boolean>> deleteTransactionById(HttpServletRequest request, 
			      @PathVariable("categoryId") Integer categoryId, @PathVariable("transactionId") Integer transactionId) {
		
		int userId = (Integer) request.getAttribute("userId");
		
		transactionService.removeTransaction(userId, categoryId, transactionId);
		
		Map<String, Boolean> map = new HashMap<>();
		map.put("Success", true);
		
		return new ResponseEntity<>(map, HttpStatus.OK);		
	}
	
	@GetMapping("/query")
	public ResponseEntity<List<Transaction>> getTransactionByCategoryName(HttpServletRequest request,
			@RequestParam(name ="categoryName", required=false) String categoryName){
		
		int userId = (Integer) request.getAttribute("userId");
		
		List<Transaction> transactions = transactionService.getTransactionByCategoryName(categoryName, userId);
		
		return new ResponseEntity<>(transactions, HttpStatus.OK);
		
	} 
			                           

}
