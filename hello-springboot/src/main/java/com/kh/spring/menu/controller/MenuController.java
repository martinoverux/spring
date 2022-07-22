package com.kh.spring.menu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.menu.model.dto.Menu;
import com.kh.spring.menu.model.dto.Taste;
import com.kh.spring.menu.model.dto.Type;
import com.kh.spring.menu.model.service.MenuService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * GET /menu
 * GET /menu/kr
 * GET /menu/ch
 * GET /menu/jp
 * 
 * GET /menu/10
 * 
 * POST /menu
 * 
 * PUT /menu
 * 
 * DELETE /menu/10
 * 
 * @RestController : @Controller + 모든 핸들러에 @ResponseBody 추가
 *
 */
@RestController
@Slf4j
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	MenuService menuService;
	
	@GetMapping
	public List<Menu> selectAll(){
		return menuService.selectAll();
	}

	@GetMapping("/type/{type}")
	public List<Menu> selectMenuByType(@PathVariable Type type){
		return menuService.selectMenuByType(type);
	}
	
	@GetMapping("/taste/{taste}")
	public List<Menu> selectMenuByTaste(@PathVariable Taste taste){
		return menuService.selectMenuByTaste(taste);
	}
	
	@PostMapping
	public ResponseEntity<?> insertMenu(@RequestBody Menu menu){
		log.debug("menu = {}", menu);
		Map<String, Object> map = new HashMap<>();
		try {
			int result = menuService.insertMenu(menu);
			map.put("msg", "메뉴를 정상적으로 등록하였습니다.");
			return ResponseEntity.ok(map);
			
		} catch (Exception e) {
			log.error("메뉴 등록 오류", e);
			map.put("msg", "이용에 불편을 드려 죄송합니다.");
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(map);
		}
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> selectMenuById(@PathVariable int id){
		Map<String, Object> map = new HashMap<>();
		
		try {
			Menu menu = menuService.selectMenuById(id);
			
			if(menu == null) {
				map = null;
				return ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.body(map);
			}
			map.put("menu", menu);
			return ResponseEntity.ok(map);
			
		} catch (Exception e) {
			log.error("메뉴 단건 조회 오류", e);
			map.put("msg", "조회 오류로 이용에 불편을 드려서 죄송합니다.");
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(map);
		}
	}
	
	
}
