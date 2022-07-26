package com.example.app.service;

import java.util.List;

import com.example.app.domain.Inventory;

public interface InventoryService {
	List<Inventory>getInventoryList() throws Exception;
	Inventory getInventoryById(Integer id) throws Exception;
	Inventory getBySchedulesIdAndTypeId(Integer schedulesId,Integer typeId) throws Exception;
	boolean checkInventory(Integer schedulesId,Integer typeId,Integer amount)throws Exception;
	void add(Inventory inventory) throws Exception;
	void update(Inventory inventory) throws Exception;
	void delete(Integer id) throws Exception;
}
