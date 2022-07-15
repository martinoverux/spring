package com.kh.spring.board.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.dto.Attachment;
import com.kh.spring.board.model.dto.Board;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardDao boardDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Board> selectBoardList(int cPage, int numPerPage) {
		int offset = (cPage -1) * numPerPage;
		int limit = numPerPage;
		RowBounds rowBounds = new RowBounds(offset, limit);
		return boardDao.selectBoardList(rowBounds);
	}
	
	@Override
	public int selectTotalContent() {
		return boardDao.selectTotalContent();
	}

	/**
	 * @Transactional은 Runtime 예외가 발생 시에만 rollback 처리
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertBoard(Board board) {
		// board insert
		int result = boardDao.insertBoard(board);
		// attachment insert
		List<Attachment> attachments = board.getAttachments();
		//log.debug("board#no = {}", board.getNo());
		if(!attachments.isEmpty()) {
			for(Attachment attach : attachments) {
				attach.setBoardNo(board.getNo());
				result = boardDao.insertAttachment(attach);
			}
		}
		return result;
	}

	@Transactional(readOnly = true)
	@Override
	public Board selectABoard(int no) {
		Board board = boardDao.selectABoard(no);
		List<Attachment> attachments = boardDao.selectAttachment(no);
		board.setAttachments(attachments);
		
		return board;
	}

	@Override
	public Board selectABoardCollection(int no) {
		return boardDao.selectABoardCollection(no);
	}

	@Override
	public int updateBoard(Board board) {
		int result = boardDao.updateBoard(board);
		List<Attachment> attachments = board.getAttachments();
		if(!attachments.isEmpty()) {
			for(Attachment attach : attachments) {
				attach.setBoardNo(board.getNo());
				result = boardDao.insertAttachment(attach);
			}
		}
		return result;
	}

	@Override
	public int deleteAttachments(int attachNo) {
		return boardDao.deleteAttachments(attachNo);
	}

	@Override
	public Attachment selectAttachmentByNo(int attachNo) {
		return  boardDao.selectAttachmentByNo(attachNo);
	}
	
}
