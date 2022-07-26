package com.example.app.service;

import java.util.List;

import com.example.app.domain.Member;

public interface MemberService {
	List<Member> getMemberList() throws Exception;
	Member getMemberById(Integer id)throws Exception;
	Member getMemberByEmail(String email)throws Exception;
	void addMember(Member member) throws Exception;
	void editMember(Member member) throws Exception;
	void deleteMember(Integer id)throws Exception;
	void linkOrder(Member member) throws Exception;
	void resetOrder(Integer id) throws Exception;

	boolean isCorrectEmailAndPassword(String email,String pass)
			throws Exception;
	int getTotalPages(int numPerPage) throws Exception;
	List<Member>getMemberListByPage(int page,int numPerPage)throws Exception;

}
