package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.OrdersDao;
import com.example.app.dao.PaymentDao;
import com.example.app.dao.SchedulesDao;
import com.example.app.dao.TypeDao;
import com.example.app.domain.Orders;
import com.example.app.domain.Payment;
import com.example.app.domain.Schedules;
import com.example.app.domain.Type;

@Service
@Transactional
public class SettingServiceImpl implements SettingService {

	@Autowired
	private OrdersDao ordersDao;
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private SchedulesDao schedulesDao;
	@Autowired
	private TypeDao typeDao;

	@Override
	public List<Orders> getOrdersList() throws Exception {
		return ordersDao.selectAll();
	}

	@Override
	public List<Payment> getPaymentList() throws Exception {
		return paymentDao.selectAll();
	}

	@Override
	public List<Schedules> getSchedulesList() throws Exception {
		return schedulesDao.selectAll();
	}

	@Override
	public List<Type> getTypeList() throws Exception {
		return typeDao.selectAll();
	}

	@Override
	public void addSchedule(Schedules schedule) throws Exception {
		schedulesDao.insert(schedule);

	}

	@Override
	public void editSchedule(Schedules schedule) throws Exception {
		schedulesDao.update(schedule);

	}

	@Override
	public void deleteSchedule(Integer scheduleId) throws Exception {
		schedulesDao.delete(scheduleId);

	}
	@Override
	public void endSchedule(Integer scheduleId) throws Exception {
		schedulesDao.end(scheduleId);

	}

	@Override
	public void addType(Type type) throws Exception {
		typeDao.insert(type);

	}

	@Override
	public void editType(Type type) throws Exception {
		typeDao.update(type);

	}
	@Override
	public void deleteType(Integer typeId) throws Exception {
		typeDao.delete(typeId);

	}

	@Override
	public void addPayment(Payment payment) throws Exception {
		paymentDao.insert(payment);
	}

	@Override
	public void editPayment(Payment payment) throws Exception {
		paymentDao.update(payment);
	}
	@Override
	public void deletePayment(Integer paymentId) throws Exception {
		paymentDao.delete(paymentId);

	}

	@Override
	public void addOrders(Orders orders) throws Exception {
		ordersDao.insert(orders);

	}

	@Override
	public void editOrders(Orders orders) throws Exception {
		ordersDao.update(orders);

	}
	@Override
	public void deleteOrders(Integer ordersId) throws Exception {
		ordersDao.delete(ordersId);

	}

	@Override
	public Schedules getScheduleById(Integer schedulesId) throws Exception {
		return schedulesDao.selectById(schedulesId);
	}

	@Override
	public Type getTypeById(Integer typeId) throws Exception {
		return typeDao.selectById(typeId);
	}

	@Override
	public Payment getPaymentById(Integer paymentId) throws Exception {
		return paymentDao.selectById(paymentId);
	}

	@Override
	public Orders getOrdersById(Integer ordersId) throws Exception {
		return ordersDao.selectById(ordersId);
	}





}
