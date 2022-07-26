package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Member;

@Mapper
public interface MemberDao {

	List<Member>selectAll() throws Exception;
	Member selectById(Integer id)throws Exception;
	Member selectByEmail(String email)throws Exception;
	void insert(Member member)throws Exception;
	void update(Member member)throws Exception;
	void delete(Integer id)throws Exception;
	void link(Member member)throws Exception;
	void reset(Integer id)throws Exception;
	Long count()throws Exception;
	List<Member>selectLimited(@Param("offset")int offset,
			@Param("numPerPage")int numPerPage)throws Exception;
}
