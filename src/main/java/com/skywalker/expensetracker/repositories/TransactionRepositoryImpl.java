package com.skywalker.expensetracker.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.skywalker.expensetracker.domain.Transaction;
import com.skywalker.expensetracker.exceptions.EtBadRequestException;
import com.skywalker.expensetracker.exceptions.EtResourceNotFoundException;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

	private static final String SQL_FIND_BY_ID = "SELECT * FROM TRANSACTIONS WHERE USER_ID = ? "
			+ "AND CATEGORY_ID = ? AND TRANSACTION_ID = ?";
	private static final String SQL_CREATE = "INSERT INTO TRANSACTIONS (USER_ID, CATEGORY_ID, "
			+ "AMOUNT, NOTE, TRANSACTION_DATE) VALUES (?, ?, ?, ?, ?)";
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Transaction> findAll(Integer userId, Integer categoryId) {
		// TODO Auto-generated method stu
		return null;
	}

	@Override
	public Transaction findById(Integer userId, Integer categoryId, Integer transactionId)
			throws EtResourceNotFoundException {
		try {
			
			return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, transactionRowMapper, new Object[] {userId, categoryId, transactionId});
			
		}catch(Exception ex) {
			
			throw new EtResourceNotFoundException("Transaction not found");
		}
	}

	@Override
	public void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction)
			throws EtBadRequestException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeById(Integer userId, Integer categoryId, Integer transactionId)
			throws EtResourceNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate)
			throws EtBadRequestException {
		try {
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			jdbcTemplate.update(connection ->{
				PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, userId);
				ps.setInt(2, categoryId);
				ps.setDouble(3, amount);
				ps.setString(4, note);
				ps.setLong(5, transactionDate);
				return ps;
				
			}, keyHolder);
			
			return (Integer) keyHolder.getKey().intValue();
			
		}catch(Exception ex) {
			throw ex;
		}
	}
	
	private RowMapper<Transaction> transactionRowMapper = ((rs,rowNum) -> {
		return new Transaction(rs.getInt("TRANSACTION_ID"),
				rs.getInt("CATEGORY_ID"),
				rs.getInt("USER_ID"),
				rs.getDouble("AMOUNT"),
				rs.getString("NOTE"),
				rs.getLong("TRANSACTION_DATE"));
	});

}
