package com.skywalker.expensetracker.service;

import java.util.List;

import com.skywalker.expensetracker.domain.Category;
import com.skywalker.expensetracker.exceptions.EtBadRequestException;
import com.skywalker.expensetracker.exceptions.EtResourceNotFoundException;

public interface CategoryService {
	
	List<Category>  fetchAllCategories(Integer userId) throws EtResourceNotFoundException;
	
	Category fetchCategoryById(Integer userId, Integer categoryId) throws EtResourceNotFoundException;
	
	Category addCategory(Integer userId, String tile, String desciption) throws EtBadRequestException;
	
	Category updateCategory(Integer userId, Integer categoryId, Category category) throws EtBadRequestException;
	
	void removeCategoryWithAllTransaction(Integer userId, Integer categoryId) throws EtResourceNotFoundException; 

}
