package com.example.app.service;

import java.util.List;

import com.example.app.domain.Member;
import com.example.app.domain.Orders;
import com.example.app.domain.Payment;
import com.example.app.domain.Progress;
import com.example.app.domain.Reserves;
import com.example.app.domain.ReservesForm;
import com.example.app.domain.Schedules;
import com.example.app.domain.Type;

public interface ReservesService {
	List<Reserves>getAllReserves() throws Exception;
	List<Reserves>getMemberReserves(Integer memberId) throws Exception;

	Reserves getById(Integer id)throws Exception;
	void addReserve(Reserves reserves)throws Exception;
	void editReserve(Reserves reserves)throws Exception;
	void deleteReserve(Reserves reserves)throws Exception;

	void visitedReserve(Integer id)throws Exception;
	void notVisitedReserve(Integer id)throws Exception;
	void paidReserve(Integer id)throws Exception;
	void notPaidReserve(Integer id)throws Exception;

	List<Member>getMemberList()throws Exception;
	List<Orders>getOrdersList()throws Exception;
	List<Payment>getPaymentList()throws Exception;
	List<Schedules>getSchedulesList()throws Exception;
	List<Type>getTypeList()throws Exception;
	Reserves changeIdToString(ReservesForm form)throws Exception;
	ReservesForm StringToChangeId(Reserves reserves)throws Exception;

	List<Reserves>getEachCount() throws Exception;
	int getTotalPrice(Reserves reserves)throws Exception;

	int getTotalPages(int numPerPage) throws Exception;
	List<Reserves>getReserveListByPage(int page,int numPerPage)throws Exception;

	int getTotalPagesByOrders(int ordersId,int numPerPage) throws Exception;
	List<Reserves>getReserveOrdersListByPage(int ordersId,int page,int numPerPage)throws Exception;

	List<Reserves>getReserveListBySchedules(Integer schedulesId)throws Exception;
	Progress getProgress(Integer schedulesId)throws Exception;
}
