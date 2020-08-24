package com.kh.view;

import java.util.*;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

// View : 사용자가 보게될 시각적 요소(화면) (입출력 ) 


public class MemberMenu {

	// Scanner 객체 생성 (전역에서 다 쓸 수 있게)
	Scanner sc = new Scanner(System.in);
	
	// MemberController 객체 생성(전역에서 바로 요청 가능하게!)
	private MemberController mc = new MemberController();
	

	/**
	 * 사용자가 보게될 첫 화면 (메인화면)
	 */
	public void mainMenu() {
		
		int menu;
		
		while(true) {
			
			
			System.out.println("\n== 회원 관리 프로그램 ==");
			System.out.println("1. 회원가입 ");
			System.out.println("2. 회원 전체 조회 ");
			System.out.println("3. 회원 아이디로 검색");
			System.out.println("4. 회원 이름으로 키워드 검색 ");
			System.out.println("5. 회원 정보 변경 ");
			System.out.println("6. 회원 탈퇴 ");
			System.out.println("0. 프로그램 종료 ");
			System.out.print("번호 선택 :  ");
			
			menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1 : insertMember(); break;
			case 2 : mc.selectList(); break;
			case 3 : String userId = inputMemberId();
					 mc.selectByUserId(userId);
					 
					 break;
			case 4 : mc.selectByUserName(inputMemberName());
					 
					 break;
					 
			case 5 : updateMember(); 
			
					 break;
					 
			case 6 : deleteMember(); break;
			case 0 : System.out.println("프로그램 종료!"); return;
			default : System.out.println("번호 다시 입력해!");
			}
			
			
		}
	}
	
	/**
	 * 회원가입 창 (화면)
	 * 즉, 회원의 정보를 입력받아 회원가입 요청 
	 */
	public void insertMember() {
		// 필수 입력 후, 추가 입력할 건지 물어보기
		
		System.out.println("\n==== 회원 가입 ====");
		
		System.out.print("아이디 : ");
		String userId = sc.nextLine();
		
		System.out.print("비밀번호 : ");
		String userPwd = sc.nextLine();
		
		System.out.print("이름 : ");
		String userName = sc.nextLine();
		
		System.out.print("성별(M/F) : ");
		String gender = sc.nextLine().toUpperCase();
		
		System.out.print("나이 : ");
		int age = sc.nextInt();
		sc.nextLine();
		
		System.out.print("이메일  : ");
		String email = sc.nextLine();
		
		System.out.print("전화번호(- 빼고 입력) : ");
		String phone = sc.nextLine();
		
		System.out.print("주소 : ");
		String address = sc.nextLine();
		
		System.out.print("취미(,로 공백없이 나열) : ");
		String hobby = sc.nextLine();
		
		
		// 회원가입 요청!! (controller 메소드 호출)
		Member m = new Member(userId, userPwd, userName, gender, age, email, phone, address, hobby);
		
		
		mc.insertMember(m);
	}
	
	
	/**
	 * 사용자 회원 아이디 입력받은 후 그입력 값 반환
	 * @return	--> 사용자가 입력한 회원 아이디
	 */
	public String inputMemberId() {
		
		System.out.print("회원 아이디 입력 : ");
		return sc.nextLine();
		
	}
	
	
	/**
	 *  사용자에게 검색할 회원명 입력 받아 값을 반환하는 메소드
	 * @return	--> 
	 */
	public String inputMemberName() {
		
		System.out.println("회원 이름(키워드) 입력  : ");
		return sc.nextLine();
		
	}
	
	/**
	 *  사용자에게 변경할 정보들 (비밀번호, 이메일, 전화번호, 주소)
	 *  해당 회원 아이디 입력 받는 화면
	 */
	public void updateMember() {
		
		System.out.println("\n==== 회원 정보 변경 ====");
		
		Member m = new Member();
		
		
		// * System.out.print("아이디 입력 : "); m.setUserId(sc.nextLine());
		
		m.setUserId(inputMemberId());	// 해당 회원 아이디 담기
		
		System.out.print("변경할 암호 : ");
		m.setUserPwd(sc.nextLine());

		System.out.print("변경할 이메일 : ");
		m.setEmail(sc.nextLine());
		
		System.out.print("변경할 전화번호 : ");
		m.setPhone(sc.nextLine());

		System.out.print("변경할 주소 : ");
		m.setAddress(sc.nextLine());
		
		// m : 변경할 회원아이디, 변경 정보 (암호, 이메일, 전화번호, 주소)
		
		// 회원정보 변경 요청
		 mc.updateMember(m);
	}
	
	
	public void deleteMember() {
		
		mc.deleteMember(inputMemberId());
		
	}
	
	//--------------------------------------------------
	
	public void displaySucess(String message) {
		System.out.println("\n서비스 요청 성공 : " + message);
	}
	
	public void displayFail(String message) {	
		System.out.println("\n서비스 요청 성공 : " + message);
	}
	
	public void displayNoData(String message) {
		System.out.println("\n" + message);
	}
	
	public void displayMemberList(ArrayList<Member> list) {
		System.out.println("\n조회된 데이터는 다음과 같다! \n");
		
		for(int i = 0; i<list.size();i++) {
			System.out.println(list.get(i));
		}
		
	}
	
	public void displayMember(Member m) {
		
		System.out.println("\n 조회된 검색결과는 다음과 같다! \n");
		
		System.out.println(m);
	}
}
