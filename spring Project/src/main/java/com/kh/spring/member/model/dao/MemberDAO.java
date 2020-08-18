package com.kh.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

@Repository
public class MemberDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	/** 로그인 DAO
	 * @param member
	 * @return loginMember
	 * @throws Exception
	 */
	public Member login(Member member) {

		return sqlSession.selectOne("memberMapper.loginMember", member);
		// memberMapper라는 namespace를 갖는 mapper파일에
		// id가 loginMember인 태그를 수행하는데 
		// 수행 시 필요한 파라미터로 member를 전달
	}


	/** 회원 가입 DAO
	 * @param signUpMember
	 * @return result
	 */
	public int signUp(Member signUpMember)  {
		
		return sqlSession.insert("memberMapper.signUp", signUpMember);
	}


	/** ID 중복 검사 DAO
	 * @param memberId
	 * @return result
	 */
	public int idDupCheck(String memberId) {

		return sqlSession.selectOne("memberMapper.idDupCheck", memberId);
	}


	/** 개인정보 수정 DAO
	 * @param upMember
	 * @return result
	 */ 
	public int updateMember(Member upMember) {
		
		return sqlSession.update("memberMapper.updateMember", upMember);
	}
	
	
}
