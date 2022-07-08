package com.kh.spring.demo.model.dao;

import java.util.List;

import com.kh.spring.demo.model.dto.Dev;

public interface DemoDao {

	int insertDev(Dev dev);

	List<Dev> selectDevList();

	Dev selectOne(int no);

	int deleteDev(int no);

	int updateDev(Dev dev);

}
