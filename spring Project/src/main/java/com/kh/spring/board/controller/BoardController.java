package com.kh.spring.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.PageInfo;
import com.kh.spring.member.model.vo.Member;

@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	 
	// @PathVariable : URL 경로에 있는 값을 파라미터로 사용할 수 있게 하는 어노테이션
	@RequestMapping("list/{type}")
	public String boardList(@PathVariable int type, 
							@RequestParam(value="cp", required=false, defaultValue="1")	int cp, 
							Model model) {
		
		System.out.println(type);
		
		// 1) Pagination 처리에 사용될 클래스 PageInfo 작성 후 bean 등록
		
		// 2) PageInfo 초기세팅
			// -> 전체 게시글 수 조회 + 현재 페이지(current Page)
			// --> start, end, max 계산
		PageInfo pInfo = boardService.pagination(type, cp);
		
		
		// 3) 게시글 목록 조회
		List<Board> boardList = boardService.selectList(pInfo);
		
		/*
		 * for(Board b : boardList) { System.out.println(b); }
		 */
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("pInfo", pInfo);
		
		return "board/boardList";
	}
	
	// 게시글 상세조회
	@RequestMapping("{type}/{boardNo}")
	public String boardView(@PathVariable int type, @PathVariable int boardNo, Model model, RedirectAttributes rdAttr, HttpServletRequest request) {
		System.out.println("type : " + type);
		System.out.println("boardNo : " + boardNo);
		
		// 게시글 하나 조회 Service
		Board board = boardService.selectBoard(boardNo);
		
		System.out.println(board);

		String url = null;
		if(board != null) { // 게시글 조회 성공 시
			model.addAttribute("board", board);
			url = "board/boardView";
				
		}else {
			// "존재하지 않는 게시글입니다" 출력 후 이전 요청 주소로 리다이렉트
			rdAttr.addFlashAttribute("status", "error");
			rdAttr.addFlashAttribute("msg", "해당 게시글이 존재하지 않습니다.");
			url = "redirect:/board/list/"+ type;
		}
		
		return url;
	}
	
	// 게시글 등록 화면 이동
	@RequestMapping("{type}/insert")
	public String insertView() {
		return "board/boardInsert";
	}
	
	// 게시글 등록
	@RequestMapping(value="{type}/insertAction", method=RequestMethod.POST)
	public String insertAction(@PathVariable int type, Board board, Model model, RedirectAttributes rdAttr) {
											
		// 게시글 등록에 필요한 내용
		
		// 카테고리, 제목, 내용, 게시판 타입, 작성자(회원번호)
		
		// Session에서 회원 정보 얻어오기 (@SessionAttributes 확인)
		Member loginMember = (Member)model.getAttribute("loginMember");
		
		// 타입, 회원번호 board에 세팅
		board.setBoardType(type);
		board.setBoardWriter(loginMember.getMemberNo()+ "");
		
		// 게시글 작성 Service 호출
		int result = boardService.insertBoard(board);
		
		String status = null;
		String msg = null;
		String url = null;
		if(result > 0) {
			status = "success";
			msg = "게시글 등록 성공";
			url = board.getBoardNo() + "?cp=1";
		}else {
			status = "error";
			msg = "게시글 등록 실패";
			url = "insert";
			
		}
		
		rdAttr.addFlashAttribute("status", "status");
		rdAttr.addFlashAttribute("msg", "msg");
		
		return "redirect:" + url;
	}
}



