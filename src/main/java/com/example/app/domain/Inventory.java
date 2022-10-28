package com.example.app.domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class Inventory {

	private Integer id;
	private Schedules schedules;
	private Type type;

	//2次開発追加
	@NotNull
	@Range(min= 0,max = 9999)
	private Integer sheet;
//↓これが変な動きしている。htmlのtexttypeをnumberで対応
//	@Pattern(regexp = "^[0-9]+$")
	//↑ここまで

}