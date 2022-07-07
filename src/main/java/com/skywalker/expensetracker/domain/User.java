package com.skywalker.expensetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	private Integer userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String DOB;
	private Integer age;

}
