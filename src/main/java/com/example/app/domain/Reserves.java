package com.example.app.domain;

import java.util.Date;

import lombok.Data;

@Data

public class Reserves {

	private Integer id;
	private Member member;
	private Schedules schedules;
	private Type type;
	private Integer amount;
	private Integer total;
	private Payment payment;
	private Orders orders;
	private Integer salesCount;
	private String note;
	private Date registered;
	private String paid;
	private String visited;

}