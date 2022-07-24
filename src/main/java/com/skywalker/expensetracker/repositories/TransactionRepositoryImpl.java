package com.skywalker.expensetracker.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
	private static final String SQL_COUNT = "SELECT COUNT(1) FROM TRANSACTIONS WHERE USER_ID = ? AND CATEGORY_ID = ?";
	private static final String SQL_FIND_ALL = "SELECT * FROM TRANSACTIONS WHERE USER_ID = ? " + "AND CATEGORY_ID = ?";
	private static final String SQL_FIND_PAGEABLE = "SELECT * FROM TRANSACTIONS WHERE USER_ID = ? "
			+ "AND CATEGORY_ID = ? LIMIT ?, ?";
	private static final String SQL_UPDATE = " UPDATE TRANSACTIONS SET AMOUNT = ?, NOTE = ?, "
			+ "TRANSACTION_DATE = ? WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ?";
	private static final String SQL_DELETE = "DELETE FROM TRANSACTIONS WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ?";
	private static final String SQL_FIND_BY_CATEGORYNAME = "SELECT t.* FROM TRANSACTIONS t JOIN CATEGORIES c ON "
			+ "t.CATEGORY_ID=c.CATEGORY_ID WHERE c.TITLE LIKE ? AND t.USER_ID=?";
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Transaction> findAll(Integer userId, Integer categoryId) {
		
		return jdbcTemplate.query(SQL_FIND_ALL, transactionRowMapper, new Object[] {userId, categoryId});
	}
	
	@Override
	public List<Transaction> findAllPageable(Integer userId, Integer categoryId, Integer pageNo, Integer pageSize) {

		//int total = jdbcTemplate.queryForObject(SQL_COUNT, new Object[] {userId, categoryId}, (rs,rowNum) -> rs.getInt(1));
		return jdbcTemplate.query(SQL_FIND_PAGEABLE, transactionRowMapper, new Object[] {userId, categoryId, pageNo*pageSize, pageSize});
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
		try {
			
			jdbcTemplate.update(SQL_UPDATE, new Object[] { transaction.getAmount(), transaction.getNote(),
					transaction.getTransactionDate(), userId, categoryId, transactionId });
			
		}catch(Exception ex) {
			//throw ex;
			throw  new EtBadRequestException("Transaction cannot be updated");
		}
	}

	@Override
	public void removeById(Integer userId, Integer categoryId, Integer transactionId)
			throws EtResourceNotFoundException {
		
			int count = jdbcTemplate.update(SQL_DELETE, new Object[] { userId, categoryId, transactionId } );
			
			if(count==0) {
				throw new EtResourceNotFoundException("Transaction not found");
			}
	}

	@Override
	public Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate)
			throws EtBadRequestException{
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
			throw new EtBadRequestException("Category Not Found");
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

	@Override
	public List<Transaction> findByCategoryName(String categoryName, Integer userId) {
		
		categoryName = categoryName+'%';
		return jdbcTemplate.query(SQL_FIND_BY_CATEGORYNAME, transactionRowMapper, new Object[] {categoryName, userId});
	}
	
}
