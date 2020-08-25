package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.MemberService;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

public class MemberController {

	public void insertMember(Member m) {
		int result = new MemberService().insertMember(m);
		
		if(result > 0) {
			new MemberMenu().displaySucess("회원 가입 성공!");
		}else {
			new MemberMenu().displayFail("회원가입 실패..");
		}
		
	}

	public void selectList() {
		
		ArrayList<Member> list = new MemberService().selectList();
		
		
		if(list.isEmpty()) {
			new MemberMenu().displayNoData("회원이 없네요?");
		} else {
			new MemberMenu().displaySelectList(list);
		}

	}
	
	public void selectByUserId(String userId) {
		
		Member m = new MemberService().selectByUserId(userId);
		
		if(m == null) {
			new MemberMenu().displayNoData("그런 회원 없소!");
		}else {
			new MemberMenu().displayMember(m);
		}
		
	}

	public void selectByUserName(String keyword) {
		
		ArrayList<Member> list = new ArrayList<Member>();
		
		list = new MemberService().selectByUserName(keyword);
		
		if(list.isEmpty()) {
			new MemberMenu().displayNoData("그런 사람 없소!!");
		}else {
			new MemberMenu().displaySelectList(list);
		}
		
	}

	public void updateMember(Member m) {
		
		int result = new MemberService().updateMember(m);
		
		if(result > 0) {
			new MemberMenu().displaySucess("정보 변경 성공!");
		}else {
			new MemberMenu().displayFail("실패했소....");
		}
		
	}

	public void deleteMember(String userId) {
		
		int result = new MemberService().deleteMember(userId);
		
		if(result > 0) {
			new MemberMenu().displaySucess("탈퇴했소 또오시오!");
		}else {
			new MemberMenu().displayFail("당신은 탈퇴할 수 없소!");
		}
		
	}
	
}
