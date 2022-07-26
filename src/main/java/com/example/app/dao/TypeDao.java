package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Type;

@Mapper
public interface TypeDao {
	List<Type> selectAll() throws Exception;

	Type selectById(Integer id) throws Exception;

	Integer selectByIdToPrice(Integer id) throws Exception;

	void insert(Type type) throws Exception;
	void update(Type type) throws Exception;
	void delete(Integer id) throws Exception;

}
