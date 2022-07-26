package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.MemberDao;
import com.example.app.dao.OrdersDao;
import com.example.app.dao.PaymentDao;
import com.example.app.dao.ReservesDao;
import com.example.app.dao.SchedulesDao;
import com.example.app.dao.TypeDao;
import com.example.app.domain.Member;
import com.example.app.domain.Orders;
import com.example.app.domain.Payment;
import com.example.app.domain.Progress;
import com.example.app.domain.Reserves;
import com.example.app.domain.ReservesForm;
import com.example.app.domain.Schedules;
import com.example.app.domain.Type;

@Service
@Transactional
public class ReservesServiceImpl implements ReservesService {

	@Autowired
	private ReservesDao reservesDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private OrdersDao ordersDao;
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private SchedulesDao schedulesDao;
	@Autowired
	private TypeDao typeDao;

	@Override
	public List<Reserves> getAllReserves() throws Exception {
		return reservesDao.selectAll();
	}

	@Override
	public List<Reserves> getMemberReserves(Integer memberId) throws Exception {
		return reservesDao.selectMemberReserves(memberId);
	}

	@Override
	public Reserves getById(Integer id) throws Exception {
		return reservesDao.selectById(id);
	}

	@Override
	public void addReserve(Reserves reserves) throws Exception {
		reservesDao.insert(reserves);

	}

	@Override
	public void editReserve(Reserves reserves) throws Exception {
		reservesDao.update(reserves);

	}

	@Override
	public void deleteReserve(Reserves reserves) throws Exception {
		reservesDao.delete(reserves);

	}

	@Override
	public List<Member> getMemberList() throws Exception {
		return memberDao.selectAll();
	}

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
	public int getTotalPrice(Reserves reserves) throws Exception {
		return (int) (reserves.getType().getPrice() * reserves.getAmount());
	}

	@Override
	public Reserves changeIdToString(ReservesForm form) throws Exception {
		Reserves reserves = new Reserves();
		Schedules schedules = schedulesDao.selectById(form.getSchedulesId());
		reserves.setSchedules(schedules);
		Type type = typeDao.selectById(form.getTypeId());
		reserves.setType(type);
		Payment payment = paymentDao.selectById(form.getPaymentId());
		reserves.setPayment(payment);
		Orders orders = ordersDao.selectById(form.getOrdersId());
		reserves.setOrders(orders);
		reserves.setAmount(form.getAmount());
		reserves.setNote(form.getNote());
		return reserves;
	}

	@Override
	public ReservesForm StringToChangeId(Reserves reserves) throws Exception {
		ReservesForm form = new ReservesForm();
		form.setSchedulesId(reserves.getSchedules().getId());
		form.setTypeId(reserves.getType().getId());
		form.setPaymentId(reserves.getPayment().getId());
		form.setOrdersId(reserves.getOrders().getId());
		form.setAmount(reserves.getAmount());
		form.setNote(reserves.getNote());
		return form;
	}

	@Override
	public int getTotalPages(int numPerPage) throws Exception {
		double totalNum = (double) reservesDao.count();
		return (int) Math.ceil(totalNum / numPerPage);
	}

	@Override
	public List<Reserves> getReserveListByPage(int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page - 1);
		return reservesDao.selectLimited(offset, numPerPage);
	}

	@Override
	public List<Reserves> getEachCount() throws Exception {
		return reservesDao.selectEachCount();
	}

	@Override
	public List<Reserves> getReserveOrdersListByPage(int ordersId, int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page - 1);
		return reservesDao.selectLimitedByOrders(ordersId, offset, numPerPage);
	}

	@Override
	public int getTotalPagesByOrders(int ordersId, int numPerPage) throws Exception {
		double totalNum = (double) reservesDao.countByOrders(ordersId);
		return (int) Math.ceil(totalNum / numPerPage);
	}

	@Override
	public List<Reserves> getReserveListBySchedules(Integer schedulesId) throws Exception {
		return reservesDao.selectBySchedules(schedulesId);
	}

	@Override
	public void visitedReserve(Integer id) throws Exception {
		reservesDao.visited(id);
	}

	@Override
	public void notVisitedReserve(Integer id) throws Exception {
		reservesDao.notVisited(id);
	}

	@Override
	public void paidReserve(Integer id) throws Exception {
		reservesDao.paid(id);
	}

	@Override
	public void notPaidReserve(Integer id) throws Exception {
		reservesDao.notPaid(id);
	}

	@Override
	public Progress getProgress(Integer schedulesId) throws Exception {
		Integer stageAmount = 0;
		Integer stageTotal = 0;
		Integer count = 0;
		Integer proceeds = 0;
		for (Reserves reserves : reservesDao.selectBySchedules(schedulesId)) {
			if (reserves.getVisited() == null) {
				stageAmount += reserves.getAmount();
				count++;
			}
			if (reserves.getPaid() == null) {
				stageTotal += reserves.getTotal();
			}
			if (reserves.getPaid() != null) {
				proceeds += reserves.getTotal();
			}
		}
		Progress progress = new Progress();
		progress.count=count;
		progress.stageAmount = stageAmount;
		progress.stageTotal = stageTotal;
		progress.proceeds = proceeds;
		return progress;
	}

}
