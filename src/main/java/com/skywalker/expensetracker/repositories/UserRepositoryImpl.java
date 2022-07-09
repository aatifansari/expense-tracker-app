package com.skywalker.expensetracker.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.skywalker.expensetracker.domain.User;
import com.skywalker.expensetracker.exceptions.EtAuthException;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	private static final String SQL_CREATE = "INSERT INTO USERS( FIRST_NAME, LAST_NAME,"
			+ " EMAIL, PASSWORD, AGE, DOB) VALUES (?, ?, ?, ?, ?, ?)";
	
	private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM USERS WHERE EMAIL=?";
	
	private static final String SQL_FIND_BY_ID = "SELECT * FROM USERS WHERE USER_ID = ?";
	
	private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, AGE, DOB "
			+ "FROM USERS WHERE EMAIL=?";
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Integer create(String firstName, String lastName, String password, String email, String DOB, Integer age) throws EtAuthException{
		
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				
				PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, firstName);
				ps.setString(2, lastName);
				ps.setString(3, email);
				ps.setString(4, hashedPassword);
				ps.setInt(5, age);
				ps.setString(6,  DOB);
				return ps;
				
			}, keyHolder);
			
			return (Integer) keyHolder.getKey().intValue();
			//return (Integer) keyHolder.getKeys().get("USER_ID");
			
		}catch(Exception ex) {
			
			throw new EtAuthException("Invalid details. Failed to create account");
		}

	}

	@Override
	public User findByEmailAndPassword(String email, String password) throws EtAuthException{
		try {
			User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, userRowMapper, new Object[] {email});
			if(!BCrypt.checkpw(password, user.getPassword()))
				throw new EtAuthException("Invalid email or password");
			return user;
		}catch(EmptyResultDataAccessException e) {
			throw new EtAuthException("Invalid email or password");
		}
		
	}

	@Override
	public Integer getCountByEmail(String email) {
		
		return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, Integer.class, new Object[] {email});
		
		

	}

	@Override
	public User findByUserId(Integer userId) {
		
		return (User) jdbcTemplate.queryForObject(
				SQL_FIND_BY_ID,
				userRowMapper,
				new Object[] {userId}
				);
	}
	
	private RowMapper<User> userRowMapper = ((rs, rowNum) ->{
		return new User(rs.getInt("USER_ID"),
				rs.getString("FIRST_NAME"),
				rs.getString("LAST_NAME"),
				rs.getString("EMAIL"),
				rs.getString("PASSWORD"),
				rs.getString("DOB"),
				rs.getInt("age")
				);
	});

}
