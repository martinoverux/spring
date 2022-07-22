package com.kh.spring.menu.model.service;

import java.util.List;

import com.kh.spring.menu.model.dto.Menu;
import com.kh.spring.menu.model.dto.Taste;
import com.kh.spring.menu.model.dto.Type;

public interface MenuService {

	List<Menu> selectAll();

	List<Menu> selectMenuByType(Type type);

	List<Menu> selectMenuByTaste(Taste taste);

	int insertMenu(Menu menu);

	Menu selectMenuById(int id);

	
}
