package com.kh.spring.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.demo.model.dto.Dev;
import com.kh.spring.demo.model.exception.DevNotFoundException;
import com.kh.spring.demo.model.service.DemoService;

import lombok.extern.slf4j.Slf4j;


/**
 * Rest API
 * - Representational State Transfer
 * - 요청 성격별로 전송방식을 결정해서 사요하는 서비스
 * - Create POST
 * - Read GET
 * - Update PUT / PATCH
 * - Delete DELETE
 * 
 * - url 작성 시에 명사형으로 계층구조를 갖도록 작성
 *
 */
@RequestMapping("/dev")
@Controller
@Slf4j
public class DevRestAPIController {

	@Autowired
	DemoService demoService;
	
	/**
	 * ResponseEntity<T>
	 * - body에 작성할 자바타입
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<?> dev(){
		List<Dev> list = null;
		try {
			list = demoService.selectDevList();
			log.debug("list = {}", list);
			
		} catch(Exception e) {
			log.error("Dev 목록 조회 오류", e);
			Map<String, Object> map = new HashMap<>();
			map.put("msg", "Dev 목록 조회 오류");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
			}
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{no}")
	public ResponseEntity<?> dev(@PathVariable int no){
		Dev dev = null;
		try {
			log.debug("no = {}", no);
			dev = demoService.selectOne(no);
			log.debug("dev = {}", dev);
			
			if(dev == null) {
				throw new DevNotFoundException(String.valueOf(no));
			}
		} catch(DevNotFoundException e) {
				return ResponseEntity.notFound().build();
		} catch(Exception e) {
			log.error("Dev 한 명 조회 오류", e);
			Map<String, Object> map = new HashMap<>();
			map.put("msg", "Dev 한 명 조회  오류");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
		
		return ResponseEntity.ok(dev);
	}
	
	/**
	 * @PathVariable에 . 이 포함된 경우 정규표현식으로 작성
	 * @param email
	 * @return
	 */
	@GetMapping("/email/{email:.+}")
	public ResponseEntity<?> dev(@PathVariable String email){
		Dev dev = null;
		try {
			
			// a. mvc 요청 새로 만들기
//			log.debug("email = {}", email);
//			dev = demoService.selectOneByEmail(email);
//			log.debug("dev = {}", dev);
 		
			// b. 전체목록에서 필터링
			  List<Dev> list = demoService.selectDevList(); for(Dev _dev : list) {
			  if(email.equals(_dev.getEmail())) { dev = _dev; break; } }
			
			
			 if(dev == null) { 
				 throw new DevNotFoundException(email); 
			 }
			 
		} catch (DevNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			log.error("Dev 한 명 이메일 조회 오류", e);
			Map<String, Object> map = new HashMap<>();
			map.put("msg", "Dev 한 명 이메일 조회  오류");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}
		log.debug("emre");
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.body(dev);
	}
	
	@GetMapping("/lang/{language}")
	public ResponseEntity<?> dev2(@PathVariable String language){
		Dev dev = null;
		List<Dev> langList = new ArrayList<>();
		try {
			// a. mvc 요청 새로 만들기
//			langList = demoService.selectListByLang(language);
					
			// b. 전체 목록에서 필터링
			List<Dev> list = demoService.selectDevList();		
			for(Dev langDev : list) {
				String[] langs = langDev.getLang();
				List<String> langLi = Arrays.asList(langs);
				if(langLi.contains(language)) {
					dev = langDev;
					langList.add(dev);
				}
			}
		
		} catch (Exception e) {
			log.error("Dev 개발 언어 조회 오류", e);
			Map<String, Object> map = new HashMap<>();
			map.put("msg", "Dev 개발 언어 조회 오류");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
		}	
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.body(langList);
	}
	
}
