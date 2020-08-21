package com.kh.spring.board.model.service;



import java.util.List;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;


public interface BoardService {

	/** 페이징 처리를 위한 Service
	 * @param type
	 * @param cp
	 * @return pInfo
	 */
	PageInfo pagination(int type, int cp);

	/** 게시글 목록 조회 Service
	 * @param pInfo
	 * @return boardList
	 */
	List<Board> selectList(PageInfo pInfo);

	/** 게시글 상세 조회 Service
	 * @param boardNo
	 * @return
	 */
	Board selectBoard(int boardNo);

	/** 게시글 등록 Service
	 * @param board
	 * @return result
	 */
	int insertBoard(Board board);
	
	
}
