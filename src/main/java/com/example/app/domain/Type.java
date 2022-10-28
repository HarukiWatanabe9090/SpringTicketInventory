package com.example.app.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class Type {

	private Integer id;

	//2次開発追加
	@Size(max = 10)
	@NotBlank(message = "券種名を入力してください")
	//↑ここまで
	private String name;

	//2次開発追加
	@Range(min= 0,max = 999999)
	@NotNull(message = "料金を入力してください")
	//↑ここまで
	private Integer price;

}