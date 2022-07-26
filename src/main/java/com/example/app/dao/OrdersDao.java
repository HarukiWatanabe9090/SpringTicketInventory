package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Orders;

@Mapper
public interface OrdersDao {
	List<Orders>selectAll() throws Exception;
	Orders selectById(Integer id) throws Exception;
	void insert(Orders orders)throws Exception;
	void update(Orders orders)throws Exception;
	void delete(Integer id)throws Exception;

}
