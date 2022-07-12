package com.kh.spring.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.dto.Member;
 
@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao memberDao;
	
	@Override public int memberEnroll(Member member) { 
		  return  memberDao.memberEnroll(member); 
	 }

	@Override
	public Member selectOneMember(String memberId) {
		return memberDao.selectOneMember(memberId);
	}

	@Override
	public int updateMember(Member member) {
		return memberDao.updateMember(member);
	}
	
}
