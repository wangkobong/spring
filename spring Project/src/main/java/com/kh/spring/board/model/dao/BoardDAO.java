package com.kh.spring.board.model.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;

@Repository
public class BoardDAO {
	
	// IOC : 제어 역전
	@Autowired // DI : 의존성 주입
	private SqlSessionTemplate sqlSession;

	/** 전체 게시글 수 조회
	 * @param type
	 * @return listCount
	 */
	public int  getListCount(int type) {
		
		return sqlSession.selectOne("boardMapper.getListCount", type);
	}

	/** 게시글 목록 조회 DAO
	 * @param pInfo
	 * @return boardList
	 */
	public List<Board> selectList(PageInfo pInfo) {
		
		// RowBounds
		// 조회된 내용 중 지정한 만큼의 수를 건너뛰고나서
		// 이후 몇개를 조회 할지를 정할 수 있는 객체
		
		
		// offset : 건너 뛸 게시글 수를 지정
		int offset = (pInfo.getCurrentPage() -1 ) * pInfo.getLimit();
						// (1 - 1) * 10 = 0 
						// (2 - 1) * 10 = 10 
						// (3 - 1) * 10 = 20 
		RowBounds rowBounds = new RowBounds(offset, pInfo.getLimit());	
		
				// selectList는 List 타입 반환
		return sqlSession.selectList("boardMapper.selectList", pInfo.getBoardType(), rowBounds);
										// 매퍼이름.태그id		, 파라미터(없으면 null)
	}

	/** 게시글 상세 조회 DAO
	 * @param boardNo
	 * @return board
	 */
	public Board selectBoard(int boardNo) {

		return sqlSession.selectOne("boardMapper.selectBoard", boardNo);
	}

	/** 조회수 증가 DAO
	 * @param boardNo
	 * @return result
	 */
	public int increaseCount(int boardNo) {
		
		return sqlSession.update("boardMapper.increaseCount", boardNo);
	}

	public int selectNextNo() {

		return sqlSession.selectOne("boardMapper.selectNextNo");
	}

	public int insertBoard(Board board) {

		return sqlSession.update("boardMapper.insertBoard", board);
	}

}
