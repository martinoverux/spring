package com.kh.spring.member.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.spring.member.model.dto.Member;

/**
 * 
 * #8. Dao 구현 클래스 없이 mapper 연결
 *
 */
@Mapper
public interface MemberDao {

	int memberEnroll(Member member);

	Member selectOneMember(String memberId);

	int updateMember(Member member);

	int insertAuthority(Member member);

	List<Member> selectMemberList();

	int deleteMemberRole(String memberId);

	int insertAuthority(Map<String, Object> param);
 
}
