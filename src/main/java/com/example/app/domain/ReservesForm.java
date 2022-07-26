package com.example.app.domain;

import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class ReservesForm {

	private Integer memberId;
	@Min(1)
	private Integer schedulesId;
	@Min(1)
	private Integer typeId;
	private Integer amount;
	@Min(1)
	private Integer paymentId;
	@Min(1)
	private Integer ordersId;
	private String note;

}
