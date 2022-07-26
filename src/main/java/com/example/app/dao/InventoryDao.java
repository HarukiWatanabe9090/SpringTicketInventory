package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Inventory;

@Mapper
public interface InventoryDao {
	List<Inventory>selectAll() throws Exception;
	Inventory selectById(Integer id) throws Exception;
	Inventory selectBySchedulesIdAndTypeId(Integer schedulesId,Integer typeId) throws Exception;
	void insert(Inventory inventory) throws Exception;
	void update(Inventory inventory) throws Exception;
	void delete(Integer id) throws Exception;

}
