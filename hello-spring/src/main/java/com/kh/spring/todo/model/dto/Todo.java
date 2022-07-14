package com.kh.spring.todo.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Todo {

	private int no;
	private String todo;
	private LocalDateTime createdAt;
	private LocalDateTime completedAt;
}
