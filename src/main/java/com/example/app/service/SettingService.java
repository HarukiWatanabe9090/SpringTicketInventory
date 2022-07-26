package com.example.app.service;

import java.util.List;

import com.example.app.domain.Orders;
import com.example.app.domain.Payment;
import com.example.app.domain.Schedules;
import com.example.app.domain.Type;

public interface SettingService {
	List<Orders>getOrdersList()throws Exception;
	List<Payment>getPaymentList()throws Exception;
	List<Schedules>getSchedulesList()throws Exception;
	List<Type>getTypeList()throws Exception;

	Schedules getScheduleById(Integer schedulesId)throws Exception;
	void addSchedule(Schedules schedule)throws Exception;
	void editSchedule(Schedules schedule)throws Exception;
	void deleteSchedule(Integer scheduleId)throws Exception;
	void endSchedule(Integer scheduleId)throws Exception;

	Type getTypeById(Integer typeId)throws Exception;
	void addType(Type type)throws Exception;
	void editType(Type type)throws Exception;
	void deleteType(Integer typeId)throws Exception;

	Payment getPaymentById(Integer paymentId)throws Exception;
	void addPayment(Payment payment)throws Exception;
	void editPayment(Payment payment)throws Exception;
	void deletePayment(Integer paymentId)throws Exception;

	Orders getOrdersById(Integer ordersId)throws Exception;
	void addOrders(Orders orders)throws Exception;
	void editOrders(Orders orders)throws Exception;
	void deleteOrders(Integer ordersId)throws Exception;
}
