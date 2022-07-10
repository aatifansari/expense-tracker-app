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

import com.skywalker.expensetracker.domain.Category;
import com.skywalker.expensetracker.exceptions.EtBadRequestException;
import com.skywalker.expensetracker.exceptions.EtResourceNotFoundException;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

	private static final String SQL_FIND_BY_ID ="SELECT C.CATEGORY_ID, C.USER_ID, C.TITLE, C.DESCRIPTION, "
			+ "COALESCE(SUM(T.AMOUNT),0) TOTAL_EXPENSE FROM TRANSACTIONS T RIGHT OUTER JOIN CATEGORIES C ON "
			+ "C.CATEGORY_ID = T.CATEGORY_ID WHERE C.USER_ID = ? AND C.CATEGORY_ID = ? GROUP BY C.CATEGORY_ID";
	private static final String SQL_CREATE = "INSERT INTO CATEGORIES(USER_ID, TITLE, DESCRIPTION) "
			+ "VALUES(?, ?, ?)";
	private static final String SQL_FIND_ALL="SELECT C.CATEGORY_ID, C.USER_ID, C.TITLE, C.DESCRIPTION, "
			+ "COALESCE(SUM(T.AMOUNT),0) TOTAL_EXPENSE FROM TRANSACTIONS T RIGHT OUTER JOIN CATEGORIES C ON "
			+ "C.CATEGORY_ID = T.CATEGORY_ID WHERE C.USER_ID = ? GROUP BY C.CATEGORY_ID";
	private static final String SQL_UPDATE = "UPDATE CATEGORIES SET TITLE = ?, DESCRIPTION = ? "
			+ "WHERE CATEGORY_ID = ? AND USER_ID = ?";
	private static final String SQL_DELETE_CATEGORY = "DELETE FROM CATEGORIES WHERE USER_ID = ? AND CATEGORY_ID = ?";
	private static final String SQL_DELETE_ALL_TRANSACTIONS = "DELETE FROM TRANSACTIONS WHERE CATEGORY_ID = ?"; 
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Category> findAll(Integer userId) throws EtResourceNotFoundException {
		try {
			System.out.println(userId);
			return jdbcTemplate.query(SQL_FIND_ALL, categoryRowMapper, new Object[] {userId});
			
		}catch(Exception ex) {
			
			throw new EtResourceNotFoundException("No category exists");
		}
	}

	@Override
	public Category findById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
		try {
			
			return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, categoryRowMapper, new Object[] {userId, categoryId});
			
		}catch(Exception ex) {
			throw new EtResourceNotFoundException("Category not found");
		}
	}

	@Override
	public Integer create(Integer userId, String title, String description) throws EtBadRequestException {

		try {
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection ->{
				PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, userId);
				ps.setString(2,  title);
				ps.setString(3, description);
				return ps;
			}, keyHolder);
			
			//return (Integer) keyHolder.getKeys().get("CATEGORY_ID");
			return (Integer) keyHolder.getKey().intValue();
			
		}catch(Exception ex) {
			
			throw ex;
			// throw new EtBadRequestException("Invalid request");			
		}
	}

	@Override
	public void update(Integer userId, Integer categoryId, Category category) throws EtBadRequestException {
		try {
			
			 jdbcTemplate.update(SQL_UPDATE, new Object[] {category.getTitle(),
					category.getDescription(), categoryId, userId});		
			
		}catch(Exception ex) {
			
			throw ex;
//			throw new EtBadRequestException("Category does not exists");
		}
	}

	@Override
	public void removeById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
		this.removeAllCatTransactions(categoryId);
		jdbcTemplate.update(SQL_DELETE_CATEGORY, new Object[] {userId, categoryId});
		
	}
	
	
	private void removeAllCatTransactions(Integer categoryId) {
		
		jdbcTemplate.update(SQL_DELETE_ALL_TRANSACTIONS, new Object[] {categoryId});
		
	}
	
	
	private RowMapper<Category> categoryRowMapper = ((rs, rowNum)->{
		
		return new Category(rs.getInt("CATEGORY_ID"),
				rs.getInt("USER_ID"),
				rs.getString("TITLE"),
				rs.getString("DESCRIPTION"),
				rs.getDouble("TOTAL_EXPENSE"));
	});
	

}
