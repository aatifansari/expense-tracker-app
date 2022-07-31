package com.skywalker.expensetracker.forms;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRegistrationForm implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Firstname required")
	private String firstName;
	
	@NotBlank(message = "Lastname required")
	private String lastName;
	
	@NotBlank(message = "Email required")
	private String email;
	
	@NotBlank(message = "Password required")
	private String password;
	
	@NotBlank(message = "Date of birth required")
	private String dateOfBirth;
}
