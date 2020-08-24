package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

// View :  클라이언트가 보게될 시각적인 요소(화면)	== 입력 및 출력

public class MemberMenu {

	private Scanner sc = new Scanner(System.in);
	private MemberController mc = new MemberController();
	
	
	/**
	 * 
	 * 	메인 페이지 == 사용자가 보게될 첫 화면 
	 */
	public void mainMenu() {
		
		int menu;
		
		while(true) {
			
			System.out.println("\n====회원 관리 프로그램==== ");
			System.out.println("1. 회원가입");
			System.out.println("2. 회원 전체 조회");
			System.out.println("3. 회원 아이디로 검색");
			System.out.println("4. 회원 이름으로 키워드 검색");
			System.out.println("5. 회원 정보 변경");
			System.out.println("6. 회원 탈퇴");
			System.out.println("0. 프로그램 종료");
			System.out.print("번호 선택 : ");
			
			menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1 : insertMember(); break;
			case 2 : mc.selectList(); break;
			case 3 : mc.selectByUserId(inputMemberId()); break;
			case 4 : mc.selectByUserName(inputMemberName()); break;
			case 5 : updateMember(); break;
			case 6 : deleteMember(); break;
			case 0 : System.out.println("프로그램 종료 할까요?(y/n) : ");
				     if(sc.nextLine().toUpperCase().charAt(0) == 'Y') {
				    	 System.out.println("프로그램 완전 종료!!"); return;
				     }else {
				    	 break;
				     }
						
					
			default : System.out.println("번호 다시 입력!!"); break;
			}
			
			
			
		}
		
		
	}
	
	
	/**
	 * 회원 아이디 입력받는 화면 (회원 아이디로 검색시 , 회원 정보수정, 회원 탈퇴)
	 * @return 사용자가 입력한 회원 아이디
	 */
	public String inputMemberId() {
		
		System.out.print("\n 회원 아이디 입력! : ");
		return sc.nextLine();
		
	}
	
	/**
	 * 회원 이름 입력 받는 화면 (회원 이름으로 검색시)
	 * @return 사용자가 입력한 회원명 (키워드)
	 */
	public String inputMemberName() {
		System.out.print("\n 회원 이름 입력 : ");
		return sc.nextLine();
	}
	
	/**
	 * 회원 가입 화면 (회원의 모든 정보 입력 받는 화면)
	 */
	public void insertMember() {
		
		System.out.println("\n==== 회원 가입 ====");
		
		String userId = this.inputMemberId();
		
		System.out.print("비밀번호 입력 : ");
		String userPwd = sc.nextLine();
		
		String userName = this.inputMemberName();
		
		System.out.print("성별(M/F) : ");
		String gender = sc.nextLine().toUpperCase();
		
		System.out.print("나이 : ");
		int age = sc.nextInt();
		sc.nextLine();
		
		System.out.print("이메일 : ");
		String email = sc.nextLine();
		
		System.out.print("전화번호(- 빼고 입력) : ");
		String phone = sc.nextLine();
		
		System.out.print("주소 : ");
		String address = sc.nextLine();
		
		System.out.println("취미(,로 공백없이 나열) : ");
		String hobby = sc.nextLine();
		
		// 회원 가입 요청 (Controller 메소드 호출 - 사용자 입력값 Member객체로 통째로 전달)
		mc.insertMember(new Member(userId,userPwd,userName,gender,age,email,phone,address,hobby));
		
		
	}
	
	
	/**
	 * 회원 정보수정 화면 (변경하고자 하는  회원 아이디, 변경할 정보(비번, 이메일, 전화번호,주소))
	 */
	public void updateMember() {
		
		System.out.println("\n === 회원 정보 수정 ===");
		
		Member m = new Member();
		
		m.setUserId(inputMemberId());;
		
		System.out.print("변경할 비번 : ");
		m.setUserPwd(sc.nextLine());
		
		System.out.print("변경할 이메일 : ");
		m.setEmail(sc.nextLine());
		
		System.out.print("변경할 전화번호 : ");
		m.setPhone(sc.nextLine());
		
		System.out.println("변경할 주소 : ");
		m.setAddress(sc.nextLine());
		
		mc.updateMember(m);
	}
	
	
	
	public void deleteMember() {
		
		System.out.println("\n === 회원 탈퇴 ===");
		
		mc.deleteMember(inputMemberId());
		
	}
	
	//------------------------------------------------------------------
	// 사용자가 서비스 요청 후 보게될 응답화면 (disPlay)
	
	
	/**
	 * 서비스 요청 성공 했을 때 응답화면
	 * @param message	--> 성공 메세지
	 */
	public void displaySucess(String message) {
		System.out.println("\n 서비스 요청 성공 : " + message);
		
	}
	
	/**
	 * 서비스 요청 실패 했을 때 응답화면
	 * @param message	--> 실패 메세지
	 */
	public void displayFail(String message) {
		System.out.println("\n 서비스 요청 실패 : " + message );
	}
	
	/**
	 * 조회 서비스 요청시 조회결과가 없을 경우의 응답화면
	 * @param message	--> 조회결과 없음 메세지
	 */
	public void displayNoData(String message) {
		System.out.println("\n" + message);
	}
	
	
	/**
	 * 조회 서비스 요청시 조회결과가 한행 이었을 경우의 응답화면
	 * @param m		조회결과에 해당하는 한 회원의 객체
	 */
	public void displayMember(Member m) {
		System.out.println("\n 조회된 회원 정보는 다음과 같습니다. \n ");
		System.out.println(m);
	}
	
	
	
	/**
	 * 조회 서비스 요청시 조회 결과가 "여러행"이였을 경우 응답 화면
	 * @param list
	 */
	public void displaySelectList(ArrayList<Member> list) {
		System.out.println("\n 조회된 회원 정보는 다음과 같습니다. \n");
		
		for(Member m : list) {
			System.out.println(m);
		}
		
		
	}
}
