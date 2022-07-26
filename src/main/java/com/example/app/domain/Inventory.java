package com.example.app.domain;

import lombok.Data;

@Data
public class Inventory {

	private Integer id;
	private Schedules schedules;
	private Type type;
	private Integer sheet;

}