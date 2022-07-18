package com.kh.spring.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.demo.model.dao.DemoDao;
import com.kh.spring.demo.model.dto.Dev;

/**
 * SqlSession 생성/반환 ->Dao DI 받아서 처리
 * dao 요청
 * 트랜잭션 처리 -> AOP로 구현
 * 
 * @author verax
 *
 */
@Service
public class DemoServiceImpl implements DemoService {

	@Autowired
	private DemoDao demoDao;

	@Override
	public int inserDev(Dev dev) {
		return demoDao.insertDev(dev);
	}
	
	@Override
	public List<Dev> selectDevList() {
		return demoDao.selectDevList();
	}

	@Override
	public Dev selectOne(int no) {
		return demoDao.selectOne(no);
	}

	@Override
	public int deleteDev(int no) {
		return demoDao.deleteDev(no);
	}

	@Override
	public int updateDev(Dev dev) {
		return demoDao.updateDev(dev);
	}

	@Override
	public Dev selectOneByEmail(String email) {
		return demoDao.selectOneByEmail(email);
	}

	@Override
	public List<Dev> selectListByLang(String language) {
		return demoDao.selectListByLang(language);
	}


}
