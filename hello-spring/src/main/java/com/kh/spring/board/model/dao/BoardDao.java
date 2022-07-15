package com.kh.spring.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import com.kh.spring.board.model.dto.Attachment;
import com.kh.spring.board.model.dto.Board;

@Mapper
public interface BoardDao {

	List<Board> selectBoardList(RowBounds rowBounds);

	@Select("select count(*) from board")
	int selectTotalContent();

	int insertBoard(Board board);
	
	@Insert("insert into attachment (no, board_no, original_filename, renamed_filename) "
			  + "values (seq_attachment_no.nextval, #{boardNo}, #{originalFilename}, #{renamedFilename})")
	int insertAttachment(Attachment attach);

	Board selectABoard(int no);

	@Select("select * from attachment where board_no = #{no}")
	List<Attachment> selectAttachment(int no);

	Board selectABoardCollection(int no);

	int updateBoard(Board board);

	@Delete("delete from attachment where no = #{attachNo}")
	int deleteAttachments(int attachNo);

	@Select("select * from attachment where no = #{attachNo}")
	Attachment selectAttachmentByNo(int attachNo);
}
