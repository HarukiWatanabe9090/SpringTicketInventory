package com.example.app.domain;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Schedules {

	private Integer id;

	//2次開発追加
	@Size(max = 30)
	//↑ここまで
	private String schedule;

	private String end;

}