package com.example.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * FORM 매핑 클래스 
 * @author ws0110
 *
 */
@Data
public class CustomerForm {

	@NotNull
	@Size(min=1, max=127)
	private String firstName;
	
	@NotNull
	@Size(min=1, max=127)
	private String lastName;
}
