package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.InventoryDao;
import com.example.app.domain.Inventory;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryDao inventoryDao;

	@Override
	public List<Inventory> getInventoryList() throws Exception {
		return inventoryDao.selectAll();
	}

	@Override
	public Inventory getInventoryById(Integer id) throws Exception {
		return inventoryDao.selectById(id);
	}

	@Override
	public Inventory getBySchedulesIdAndTypeId(Integer schedulesId, Integer typeId) throws Exception {
		return inventoryDao.selectBySchedulesIdAndTypeId(schedulesId, typeId);
	}

	@Override
	public boolean checkInventory(Integer schedulesId, Integer typeId, Integer amount) throws Exception {
		Inventory inventory = inventoryDao.selectBySchedulesIdAndTypeId(schedulesId, typeId);
		if ((inventory.getSheet() - amount) < 0) {
			return false;
		}
		return true;
	}

	@Override
	public void add(Inventory inventory) throws Exception {
		inventoryDao.insert(inventory);
	}

	@Override
	public void update(Inventory inventory) throws Exception {
		inventoryDao.update(inventory);
	}

	@Override
	public void delete(Integer id) throws Exception {
		inventoryDao.delete(id);
	}

}
