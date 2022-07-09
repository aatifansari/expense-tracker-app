package com.skywalker.expensetracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skywalker.expensetracker.domain.Category;
import com.skywalker.expensetracker.exceptions.EtBadRequestException;
import com.skywalker.expensetracker.exceptions.EtResourceNotFoundException;
import com.skywalker.expensetracker.repositories.CategoryRepository;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Override
	public List<Category> fetchAllCategories(Integer userId) throws EtResourceNotFoundException {
		
		return categoryRepository.findAll(userId);
	}

	@Override
	public Category fetchCategoryById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
		
		return categoryRepository.findById(userId, categoryId);
	}

	@Override
	public Category addCategory(Integer userId, String tile, String desciption) throws EtBadRequestException {
		
		int categoryId = categoryRepository.create(userId, tile, desciption);
		System.out.println("Category_Id "+categoryId);
		
		return categoryRepository.findById(userId, categoryId);
	}

	@Override
	public Category updateCategory(Integer userId, Integer categoryId, Category category) throws EtBadRequestException {
		
		categoryRepository.update(userId, categoryId, category);
		
		return categoryRepository.findById(userId, categoryId);

	}

	@Override
	public void removeCategoryWithAllTransaction(Integer userId, Integer categoryId)
			throws EtResourceNotFoundException {
		// TODO Auto-generated method stub

	}

}
