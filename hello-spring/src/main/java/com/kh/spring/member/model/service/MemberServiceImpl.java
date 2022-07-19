package com.kh.spring.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.dto.Member;
 
@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao memberDao;
	
	@Transactional(rollbackFor = Exception.class)
	@Override 
	public int memberEnroll(Member member) { 
		int result = memberDao.memberEnroll(member);
		result = memberDao.insertAuthority(member);
		return result;  
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
