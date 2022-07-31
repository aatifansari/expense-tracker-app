  package com.skywalker.expensetracker.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skywalker.expensetracker.domain.User;
import com.skywalker.expensetracker.exceptions.DateFormatException;
import com.skywalker.expensetracker.exceptions.EtAuthException;
import com.skywalker.expensetracker.exceptions.EtBadRequestException;
import com.skywalker.expensetracker.repositories.UserRepository;
import com.skywalker.expensetracker.utils.Helper;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User validateUser(String email, String password) throws EtAuthException {
		
		if(email != null) email=email.toLowerCase();
		return userRepository.findByEmailAndPassword(email, password);
	}
	
	@Override
	public User registerUser(String firstName, String lastName, String email, String password, String dob)
            throws EtAuthException{
		
		// validate firstname and lastname
		if(Helper.validateUsername(firstName) || Helper.validateUsername(lastName)) {
			throw new EtBadRequestException("FirstName and Lastname cannot be number");
		}
		Pattern pattern = Pattern.compile("^(.+)@(.+)$");
		if(email != null) {
			email.toLowerCase();
		}
		if(!pattern.matcher(email).matches()) {
			throw new EtAuthException("Invalid email format");
		}
		Integer count = userRepository.getCountByEmail(email);
		if(count>0) {
			throw new EtAuthException("Email already in use");
		}

		//get age from dob
		int calculatedAge;
		try {
			calculatedAge = Helper.findAge(dob);
		}catch(Exception ex) {
			throw new DateFormatException(ex.getMessage());
		}
		Integer userId = userRepository.create(firstName, lastName, password, email, dob, calculatedAge);
		return userRepository.findByUserId(userId);
	}
}
