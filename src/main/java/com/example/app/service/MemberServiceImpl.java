package com.example.app.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.MemberDao;
import com.example.app.domain.Member;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;

	@Override
	public boolean isCorrectEmailAndPassword(String email, String pass) throws Exception {
		Member member = memberDao.selectByEmail(email);
		if(member == null) {
			return false;
		}
		if(!BCrypt.checkpw(pass, member.getPassword())) {
			return false;
		}
		return true;
	}

	@Override
	public List<Member> getMemberList() throws Exception {
		return memberDao.selectAll();
	}

	@Override
	public Member getMemberById(Integer id) throws Exception {
		return memberDao.selectById(id);
	}

	@Override
	public Member getMemberByEmail(String email) throws Exception {
		return memberDao.selectByEmail(email);
	}

	@Override
	public void addMember(Member member) throws Exception {
		member.setPassword(BCrypt.hashpw(member.getPassword(),BCrypt.gensalt()));
		memberDao.insert(member);
	}

	@Override
	public void editMember(Member member) throws Exception {
		member.setPassword(BCrypt.hashpw(member.getPassword(),BCrypt.gensalt()));
		memberDao.update(member);
	}

	@Override
	public void deleteMember(Integer id) throws Exception {
		memberDao.delete(id);

	}

	@Override
	public void linkOrder(Member member) throws Exception {
		memberDao.link(member);

	}

	@Override
	public void resetOrder(Integer id) throws Exception {
		memberDao.reset(id);
	}

	@Override
	public int getTotalPages(int numPerPage) throws Exception {
		double totalNum = (double)memberDao.count();
		return (int)Math.ceil(totalNum/numPerPage);
	}

	@Override
	public List<Member> getMemberListByPage(int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page-1);
		return memberDao.selectLimited(offset, numPerPage);
	}


}
