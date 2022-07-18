package com.kh.spring.demo.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.spring.demo.model.dto.Dev;

@Mapper
public interface DemoDao {

	int insertDev(Dev dev);

	List<Dev> selectDevList();

	Dev selectOne(int no);

	int deleteDev(int no);

	int updateDev(Dev dev);

	Dev selectOneByEmail(String email);

	List<Dev> selectListByLang(String language);

}
