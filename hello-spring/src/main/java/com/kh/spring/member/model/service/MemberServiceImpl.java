package com.kh.spring.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", member.getMemberId());
		map.put("auth", MemberService.ROLE_USER);
		result = memberDao.insertAuthority(member); // ROLE_USER
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

	@Override
	public List<Member> selectMemberList() {
		return memberDao.selectMemberList();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateMemberRole(String memberId, List<String> authorities) {
		// 기존권한 삭제
		int result = memberDao.deleteMemberRole(memberId);
		// 새 권한 등록
		for(String auth : authorities) {
			Map<String, Object> param = new HashMap<>();
			param.put("memberId", memberId);
			param.put("auth", auth);
			result = memberDao.insertAuthority(param);			
		}
		
		return result;
	}

}
