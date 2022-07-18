package com.kh.spring.demo.model.service;

import java.util.List;

import com.kh.spring.demo.model.dto.Dev;

public interface DemoService {

	int inserDev(Dev dev);

	List<Dev> selectDevList();

	Dev selectOne(int no);

	int deleteDev(int no);

	int updateDev(Dev dev);

	Dev selectOneByEmail(String email);

	List<Dev> selectListByLang(String language);



}
