package com.example.app.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.app.validation.AddGroup;
import com.example.app.validation.LoginGroup;

import lombok.Data;

@Data
public class Member {
	private Integer id;

	@NotBlank(groups = {AddGroup.class})
	@Size(max=20,groups= {AddGroup.class})
	private String name;

	@NotBlank(groups = {AddGroup.class})
	@Size(max=20,groups= {AddGroup.class})
	private String furigana;

	@NotBlank(groups = {AddGroup.class,LoginGroup.class})
	@Size(max=255,groups = {AddGroup.class})
	@Email(groups = {AddGroup.class})
	private String email;

	@NotBlank(groups = {AddGroup.class,LoginGroup.class})
	@Pattern(regexp = "^[a-zA-Z0-9]+$",groups = {AddGroup.class})
	private String password;

	private String password2;

	private String status;

	private Orders orders;

}
