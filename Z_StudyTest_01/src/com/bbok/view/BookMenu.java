package com.bbok.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.bbok.controller.BookManager;
import com.bbok.model.vo.Book;

public class BookMenu {

	Scanner sc = new Scanner(System.in);
	BookManager bm = new BookManager();
	
	public void mainMenu() {
		
		while(true) {
			
			System.out.println("\n **** 도서 관리 프로그램 ****");
			
			System.out.println("1. 새 도서 추가");
			System.out.println("2. 도서 삭제");
			System.out.println("3. 도서 검색 출력");
			System.out.println("4. 전체 출력");
			System.out.println("0. 끝내기");
			
			System.out.println("메뉴 번호 선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1 : insertBook(); break;
			case 2 : deleteBook(); break;
			case 3 : searchBook(); break;
			case 4 : break;
			case 0 : break;
			default : System.out.println("번호 다시 누르세요!"); return;
			}
			
			
		}
		
		
		
	}
	
	public String inputBookTitle() {
		
		System.out.print("책 이름을 입력하세요 ! : ");
		return sc.nextLine();
		
	}
	
	
	
	public void insertBook() {
		
		System.out.print("도서 제목 : ");
		String title = sc.nextLine();
		System.out.print("도서 장르(1:인문 / 2: 자연과학/ 3 : 의료 / 4: 기타): ");
		int category = sc.nextInt();
		sc.nextLine();
		System.out.print("도서 저자 : ");
		String author = sc.nextLine();
		
		Book book = new Book(title, category, author);
		
		bm.insertBook(book);
		
		
	}
	
	
	public void deleteBook() {
		
		System.out.println("\n == 도서를 삭제 합니다 ==");
		
		bm.deleteBook(inputBookTitle());
		
		
	}
	
	public void searchBook() {
		
		bm.searchBook(inputBookTitle());
		
	}
	
//==========================================================================
	
	public void displaySucess(String message) {
		System.out.println("성공 여부 : " + message);
	}
	
	public void displayFail(String message) {
		System.out.println("실패 여부 : " + message);
	}
	
	public void displaySelectList(ArrayList<Book> list) {
		
		System.out.println("\n 책을 조회합니다!");
		
		for(Book book : list) {
			System.out.println(book);
		}
	
	}
	
	
	public void displayNoData(String message) {
		System.out.println("조회 결과 :" + message);
	}
}
