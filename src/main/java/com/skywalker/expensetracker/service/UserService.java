package com.skywalker.expensetracker.service;

import com.skywalker.expensetracker.domain.User;
import com.skywalker.expensetracker.exceptions.EtAuthException;

public interface UserService {
	
	User validateUser(String email, String password) throws EtAuthException;
	
	User registerUser(String firstName, String lastName, String email, String password, String dob)
	                 throws EtAuthException;
	

}
