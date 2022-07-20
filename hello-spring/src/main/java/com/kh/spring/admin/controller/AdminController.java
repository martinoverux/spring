package com.kh.spring.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

	@Autowired
	MemberService memberService;
	
	@GetMapping("/memberList.do")
	public void memberList(Model model) {
		log.debug("/admin/memberList.do 요청!");
		try {
			List<Member> list = memberService.selectMemberList();
			log.debug("list = {}", list);
			
			model.addAttribute("list", list);
		} catch(Exception e) {
			log.error("회원관리 오류!", e);
			throw e;
		}
	}
	
	@PostMapping("/memberRoleUpdate.do")
	public ResponseEntity<?> memberRoleUpdate(@RequestBody Member member){
		Map<String, Object> map = new HashMap<>();
		
		log.debug("member = {}", member);
		List<String> authorities = new ArrayList<>();
		for(GrantedAuthority auth : member.getAuthorities()) {
			authorities.add(auth.getAuthority());
		}
		try {
			int result = memberService.updateMemberRole(member.getMemberId(), authorities);
			map.put("msg", "관리자 회원권한을 정상적으로 수정했습니다.");
			
		} catch (Exception e) {
			log.error("관리자 회원권하 수정 오류", e);
			map.put("msg", "관리자 회원권한 수정 오류");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
		return ResponseEntity.ok(map);
	}
	/*
	 * @PostMapping("/memberRoleUpdate.do") public ResponseEntity<?>
	 * memberRoleUpdate(@RequestParam String
	 * memberId, @RequestParam("authorities[]") List<String> authorities){
	 * Map<String, Object> map = new HashMap<>();
	 * 
	 * log.debug("memberId = {}", memberId); log.debug("authorities = {}, {}",
	 * authorities, authorities);
	 * 
	 * try { int result = memberService.updateMemberRole(memberId, authorities);
	 * map.put("msg", "관리자 회원권한을 정상적으로 수정했습니다.");
	 * 
	 * } catch (Exception e) { log.error("관리자 회원권하 수정 오류", e); map.put("msg",
	 * "관리자 회원권한 수정 오류"); return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map); } return
	 * ResponseEntity.ok(map); }
	 */	
}
