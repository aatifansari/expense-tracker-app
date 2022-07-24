package com.skywalker.expensetracker.resources;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skywalker.expensetracker.Constants;
import com.skywalker.expensetracker.domain.User;
import com.skywalker.expensetracker.forms.UserRegistrationForm;
import com.skywalker.expensetracker.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(value="/api/users")
public class UserResource {
	
	@Autowired
	UserService userService;
	
	@PostMapping(value="/login")
	public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap){
		String email = (String) userMap.get("email");
		String password = (String) userMap.get("password");
		User user = userService.validateUser(email, password);
		return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
	}
	
	@PostMapping(value = "/register" , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Map<String,String>> registerUser(@RequestBody @Validated UserRegistrationForm userRegisForm) {
		
		String firstName = (String) userRegisForm.getFirstName();
		String lastName = (String) userRegisForm.getLastName();
		String email = (String) userRegisForm.getEmail();
		String password = (String) userRegisForm.getPassword();
		String DOB = (String) userRegisForm.getDateOfBirth();
		User user = userService.registerUser(firstName, lastName, email, password, DOB);
		System.out.println(user);
		return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
	}
	
	private Map<String, String> generateJWTToken(User user){
		
		long timestamp = System.currentTimeMillis();
		String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
				.claim("userId", user.getUserId())
				.claim("email", user.getEmail())
				.claim("firstName", user.getFirstName())
				.claim("lastName", user.getLastName())
				.claim("age", user.getAge())
				.compact();
		Map<String, String> map = new HashMap<>();
		map.put("token", token);
		return map;
	}
	
}
