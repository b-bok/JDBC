package com.kh.controller;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

// Controller : 사용자가 View를 통해서 요청한 기능을 처리하는 담당
//				해당 메소드로 전달된 데이터 가공 처리후 Dao로 전달 (Dao 메소드 전달)
// 				Dao로부터 반환 받은 결과에 따라 성공 또는 실패 View 결정  
public class MemberController {

	/**
	 * 사용자의 회원가입 요청을 처리해주는 메소드
	 * @param m	--> 사용자가 입력한 정보들이 잔뜩 담긴  Member 객체
	 */
	public void insertMember(Member m) {
		
		int result = new MemberDao().insertMember(m);
		
		if (result > 0) { // 성공했을 때
			
			new MemberMenu().displaySucess("회원가입 성공");
			
			
		}else { // 실패했을 때
			
			new MemberMenu().displayFail("회원가입 실패");
		}
	}

}	
