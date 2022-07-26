package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Reserves;

@Mapper
public interface ReservesDao {
	List<Reserves>selectAll() throws Exception;
	List<Reserves>selectMemberReserves(Integer memberId) throws Exception;
	Reserves selectById(Integer id)throws Exception;
	void insert(Reserves reserves)throws Exception;
	void update(Reserves reserves)throws Exception;
	void delete(Reserves reserves)throws Exception;

	void visited(Integer id)throws Exception;
	void notVisited(Integer id)throws Exception;
	void paid(Integer id)throws Exception;
	void notPaid(Integer id)throws Exception;

	List<Reserves>selectEachCount() throws Exception;

	Long count()throws Exception;
	List<Reserves>selectLimited(@Param("offset")int offset,
			@Param("numPerPage")int numPerPage)throws Exception;

	Long countByOrders(Integer ordersId)throws Exception;
	List<Reserves>selectLimitedByOrders(@Param("id")int id,@Param("offset")int offset,
			@Param("numPerPage")int numPerPage)throws Exception;

	List<Reserves>selectBySchedules(Integer schedulesId)throws Exception;


}
