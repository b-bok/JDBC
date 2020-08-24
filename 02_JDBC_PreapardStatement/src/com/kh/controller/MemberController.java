package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

// Controller : 클라이언트의 요청 받아서 처리한 응답뷰 (성공/실패 , 조회 유무) 결정
public class MemberController {

	
	
	/**
	 * 사용자가 회원 가입 요청시 실행되는 메소드
	 * @param m		회원 가입 화면에서 사용자가 입력한 회원 정보들이 담겨 있는 Member객체
	 * @return
	 */
	public void insertMember(Member m) {
		
		int result = new MemberDao().insertMember(m);
		
		if(result > 0) {
			
			new MemberMenu().displaySucess("회원 가입 성공!!");
			
		}else {
			
			new MemberMenu().displayFail("회원 가입 실패...");
		}
	}
	
	public void selectList() {
		
		ArrayList<Member> list = new MemberDao().selectList();
		
		if(list.isEmpty()) {
			new MemberMenu().displayNoData("회원이 없소...");
		}else {
			new MemberMenu().displaySelectList(list);
		}
		
	}
	
	public void selectByUserId(String userId) {
		
		Member m = new MemberDao().selectByUserId(userId);
		
		
		if(m == null) {
			new MemberMenu().displayNoData("조회 결과 없어요..");
		}else {
			new MemberMenu().displayMember(m);
		}
		
	}
	
	public void selectByUserName(String userName) {
		
		ArrayList<Member> list = new MemberDao().selectByUserName(userName);
		
		if(list.isEmpty()) {
			new MemberMenu().displayNoData("없네요....");
		} else {
			new MemberMenu().displaySelectList(list);
		}
		
	}
	
	
	public void updateMember(Member m) {
		
		int result = new MemberDao().updateMember(m);
		
		if(result > 0 ) {
			new MemberMenu().displaySucess("정보 변경 성공!");
		}else {
			new MemberMenu().displayFail("변경 실패...");
		}
	}

	public void deleteMember(String userId) {
		
		int result = new MemberDao().deleteMember(userId);
		
		if(result > 0) {
			new MemberMenu().displaySucess("회원 탈퇴 성공!");
		}else {
			new MemberMenu().displayFail("회원 탈퇴 실패....");
		}
		
	}
}
