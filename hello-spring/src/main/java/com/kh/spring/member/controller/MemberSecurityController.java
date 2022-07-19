package com.kh.spring.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
	
}
