package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Schedules;

@Mapper
public interface SchedulesDao {
	List<Schedules>selectAll()throws Exception;
	Schedules selectById(Integer id)throws Exception;
	void insert(Schedules schedules)throws Exception;
	void update(Schedules schedules)throws Exception;
	void delete(Integer id)throws Exception;
	void end(Integer id)throws Exception;

}
