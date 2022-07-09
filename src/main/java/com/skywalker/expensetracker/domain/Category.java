package com.skywalker.expensetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
	
	private Integer categoryId;
	private Integer userId;
	private String title;
	private String description;
	private Double totalExpense;
	
	public Category(Integer categoryId, Integer userId, String title, String description) {
		super();
		this.categoryId = categoryId;
		this.userId = userId;
		this.title = title;
		this.description = description;
	}
	
	
}
