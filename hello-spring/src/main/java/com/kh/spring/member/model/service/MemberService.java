package com.kh.spring.member.model.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.kh.spring.member.model.dto.Member;

public interface MemberService  {

	int memberEnroll(Member member);

	Member selectOneMember(String memberId);

	int updateMember(Member member);

}
