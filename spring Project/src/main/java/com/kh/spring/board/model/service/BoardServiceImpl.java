package com.kh.spring.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.board.model.dao.BoardDAO;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDAO boardDAO;
	
	@Autowired // 의존성 주입(DI)
	private PageInfo pInfo;
	
	
	// 페이징 처리를 위한 Service 구현
	@Override
	public PageInfo pagination(int type, int cp) {
		
		// 1) 전체 게시글 수 조회
		int listCount = boardDAO.getListCount(type);
		
		// 2) setPageInfo
		pInfo.setPageInfo(cp, listCount, type);
		
		return pInfo;
		
	
	}


	@Override
	public List<Board> selectList(PageInfo pInfo) {

		return boardDAO.selectList(pInfo);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Board selectBoard(int boardNo) {
		
		// 게시글 조회
		Board board = boardDAO.selectBoard(boardNo);
	
		
		// 조회 성공 시 조회 수 증가
		if(board != null) {
			int result = boardDAO.increaseCount(boardNo);
			
			// 조회된 게시글(board)의 조회수를 1 증가
			if(result > 0) {
				board.setReadCount(board.getReadCount() + 1);
			}
		}
		
		return board;
	}

	// 게시글 등록 Service 구현
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertBoard(Board board) {
		
		int result = 0;
		
		// 1) 다음 SEQ_BNO를 얻어옴.
		int boardNo = boardDAO.selectNextNo();
		
		if(boardNo > 0) { // 다음 번호를 정상적으로 얻어 왔을 때
			// 다음 번호 board 객체에 세팅
			board.setBoardNo(boardNo);
			
			// 크로스 사이트 스크립트 방지 처리
			board.setBoardContent(replaceParameter(board.getBoardContent()));
			
			// 2) 게시글(board) DB 삽입
			result = boardDAO.insertBoard(board);
		}
		
		return result;
	}
	
	// 크로스 사이트 스크립트 방지 메소드
    private String replaceParameter(String param) {
        String result = param;
        if(param != null) {
            result = result.replaceAll("&", "&amp;");
            result = result.replaceAll("<", "&lt;");
            result = result.replaceAll(">", "&gt;");
            result = result.replaceAll("\"", "&quot;");
        }

        return result;
    }
	
}





