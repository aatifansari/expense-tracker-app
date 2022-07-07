package com.skywalker.expensetracker.repositories;

import com.skywalker.expensetracker.domain.User;

public interface UserRepository {
	
	Integer create(String firstName, String lastName, String password, String email, String DOB, Integer age );
	
	User findByEmailAndPassword(String email, String password);
	
	Integer getCountByEmail(String email);
	
	User findByUserId(Integer userId);
	
}
