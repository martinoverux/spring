package com.kh.spring.todo.model.service;

import java.util.List;
import java.util.Map;

import com.kh.spring.todo.model.dto.Todo;

public interface TodoService {

	int insertTodo(Todo todo);

	List<Todo> selectTodoList();

	int updateIsCompleted(Map<String, Object> param);
	
	
}
