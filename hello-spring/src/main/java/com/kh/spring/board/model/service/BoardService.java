package com.kh.spring.board.model.service;

import java.util.List;

import com.kh.spring.board.model.dto.Attachment;
import com.kh.spring.board.model.dto.Board;

public interface BoardService {

	List<Board> selectBoardList(int cPage, int numPerPage);

	int selectTotalContent();

	int insertBoard(Board board);

	Board selectABoard(int no);

	Board selectABoardCollection(int no);

	int updateBoard(Board board);

	int deleteAttachments(int attachNo);

	Attachment selectAttachmentByNo(int attachNo);
 

}
