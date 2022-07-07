package com.skywalker.expensetracker.resources;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/categories")
public class CategoryResource {
	
	@GetMapping("")
	public String getAllCategories(HttpServletRequest request) {
		
		int userId = (Integer) request.getAttribute("userId");
		return "Authenticated: Userid: " + userId;
	}
	
}
