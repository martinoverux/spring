package com.kh.spring.menu.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.spring.menu.model.dto.Menu;
import com.kh.spring.menu.model.dto.Taste;
import com.kh.spring.menu.model.dto.Type;

@Mapper
public interface MenuDao {

	@Select("select * from menu order by id desc")
	List<Menu> selectALl();

	@Select("select * from menu where type = #{type} order by id desc")
	List<Menu> selectMenuByType(Type type);

	@Select("select * from menu where taste = #{taste} order by id desc")
	List<Menu> selectMenuByTaste(Taste taste);

	@Insert("insert into menu values(seq_menu_id.nextval, #{restaurant}, #{name}, #{price}, #{type}, #{taste})")
	int insertMenu(Menu menu);

	@Select("select * from menu where id = #{id}")
	Menu selectMenuById(int id);
}
