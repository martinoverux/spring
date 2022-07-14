package com.kh.spring.todo.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.spring.todo.model.dto.Todo;

@Mapper
public interface TodoDao {

	int insertTodo(Todo todo);

	List<Todo> selectTodoList();

	int updateIsCompleted(Map<String, Object> param);

}
