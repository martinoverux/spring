package com.kh.spring.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberSecurityController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;
	
	// @RequestMapping(path="/memberEnroll.do", method=RequestMethod.GET)
	@GetMapping("/memberEnroll.do")
	public void memberEnroll() {}
	
	/**
	 * $2a$10$yGixdofbXZOAh1qCeB5Ruuo2yHUAx9xjwoao6tYT5PZ329T3FvQnO
	 *
	 * - 알고리즘 $2a$
	 * - 옵션 10$ 속도/메모리 요구량
	 * - 랜덤 솔트 22자리 yGixdofbXZOAh1qCeB5Ruu
	 * - 해시 31자리 o2yHUAx9xjwoao6tYT5PZ329T3FvQnO
	 * 
	 * @param member
	 * @param redirectAttr
	 * @return
	 */
	@PostMapping("/memberEnroll.do")
	public String memberEnroll(Member member, RedirectAttributes redirectAttr) {
		log.info("member = {}", member);
		try {
			
			// 암호화 처리
			String rawPassword = member.getPassword();
			String encryptedPassword = bcryptPasswordEncoder.encode(rawPassword);
			member.setPassword(encryptedPassword);
			log.info("encryptedPassword = {}", encryptedPassword);
			
			// service에 insert 요청
			int result = memberService.memberEnroll(member);
			
			// 사용자 처리 피드백
			redirectAttr.addFlashAttribute("msg", "가입이 정상적으로 완료되었습니다.");	
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "redirect:/";
	}
	
	@GetMapping("/memberLogin.do")
	public void memberLogin() {}
	
	/**
	 * ResponseEntity
	 * - 응답메시지 작성을 도와주는 객체. status, header, body 자유롭게 작성가능
	 * - @ResponseBody 포함
	 * @param memberId
	 * @return
	 */
	@GetMapping("/checkIdDuplicate.do")
	public ResponseEntity<?> checkIdDuplicate3(@RequestParam String memberId) {
		Map<String, Object> map = new HashMap<>();
		try {
			Member member = memberService.selectOneMember(memberId);
			boolean available = member == null;
			
			map.put("memberId", memberId);
			map.put("available", available);
			
		} catch (Exception e) {
			log.error("중복아이디 체크 오류", e);
		//	throw e;
			
			map.put("error", e);
			map.put("msg", "이용에 불편을 드려 죄송합니다.");
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
					.body(map);
		}
	//	return ResponseEntity.ok(map); // 200 + body에 작성할 맵
		return ResponseEntity
					.status(HttpStatus.OK)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
					.body(map);
	}
	
	/**
	 * SecurityContextHolder - SecurityContext - Authentication에 보관중인 로그인한 사용자 정보 가져오기
	 * 
	 * - Principal
	 * - Credentials
	 * - Authorities
	 * 
	 * 1. SecurityContextHolder로부터 Authentication 가져오기
	 * 2. 핸들러의 인자로 받기
	 * 3. @AuthenticationPrincipal 통해서 Principal 객체 직접 받기
	 */
	@GetMapping("/memberDetail.do")
//	public void memberDetail(Authentication authentication) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		Member principal = (Member) authentication.getPrincipal();
//		log.debug("pricipal = {}", principal); //  Member(super=MemberEntity(memberId=honggd, password=$2a$10$yGixdofbXZOAh1qCeB5Ruuo2yHUAx9xjwoao6tYT5PZ329T3FvQnO, name=홍길동, gender=M, birthday=2000-03-17, email=honggd@naver.com, phone=01012347892, address=서울시 성북구 성북동, hobby=[등산, 독서, 여행], createdAt=2022-07-11T14:12:36, updatedAt=2022-07-12T12:39:43, enabled=true), authorities=[ROLE_USER])
//		
//		Object credentials = authentication.getCredentials();
//		log.debug("credentials = {}", credentials); // null
//		
//		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
//		log.debug("authorities = {}", authorities); // [ROLE_USER]
//		
//	}
	public void memberDetail(@AuthenticationPrincipal Member member) {
		log.debug("member = {}", member);
	}
	
	@PostMapping("/memberUpdate.do")
	public ResponseEntity<?> memberUpdate(Member updateMember, @AuthenticationPrincipal Member loginMember){
		log.debug("updateMember ={}", loginMember);
		log.debug("updateMember ={}", updateMember);
		Map<String, Object> map = new HashMap<>();
		
		try {
			// 1. db 갱신
			int result = memberService.updateMember(updateMember);
			map.put("msg", "회원정보를 성공적으로 변경하였습니다.");
			
			// 2. security가 관리하는 session 없데이트
			loginMember.setName(updateMember.getName());
			loginMember.setBirthday(updateMember.getBirthday());
			loginMember.setEmail(updateMember.getEmail());
			loginMember.setPhone(updateMember.getPhone());
			loginMember.setAddress(updateMember.getAddress());
			loginMember.setGender(updateMember.getGender());
			loginMember.setHobby(updateMember.getHobby());
		 
			// 비밀번호/권한 정보가 바뀌었을 대는 전체 Authentication을 대체
			Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
					loginMember, 
					loginMember.getPassword(), 
					loginMember.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(newAuthentication);
		 
		} catch (Exception e) {
			map.put("회원정보 수정 오류!", e);
			map.put("msg", "이용에 불편을 드려 죄송합니다.");
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(map);
		}
		
		return ResponseEntity.ok(map);
		
	}
}
