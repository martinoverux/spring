package com.kh.spring.board.model.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class Board extends BoardEntity {
	private int attachCount;

	public Board(int no, String title, String memberId, String content, LocalDateTime createdAt,
			LocalDateTime updatedAt, int readCount, int attachCount) {
		super(no, title, memberId, content, createdAt, updatedAt, readCount);
		this.attachCount = attachCount;
	}
	
}
