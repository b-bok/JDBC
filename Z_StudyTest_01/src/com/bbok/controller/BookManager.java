package com.bbok.controller;

import java.util.ArrayList;

import com.bbok.model.dao.BookDao;
import com.bbok.model.vo.Book;
import com.bbok.view.BookMenu;

public class BookManager {

	public void insertBook(Book book) {
		
		int result = new BookDao().insertBook(book);
		
		if (result > 0) {
			
			new BookMenu().displaySucess("데이터 생성 성공!");
			
		}else {
			
			new BookMenu().displayFail("데이터 생성 실패...");
		}
		
	}

	
	public void deleteBook(String bookTitle) {
		
		int result = new BookDao().deleteBook(bookTitle);
		
		
		if(result > 0) {
			new BookMenu().displaySucess("책 삭제 성공!");
		} else {
			new BookMenu().displayFail("책 삭제 실패...");
		}
		
	}
	
	public void searchBook(String bookTitle) {
		
		ArrayList list = new BookDao().searchBook(bookTitle);
		
		
		if(list.isEmpty()) {
			
			new BookMenu().displaySelectList(list);
			
			
		}else {
			
			new BookMenu().displayNoData("조회 결과가 없어요...");
		}
		
	}
	
}
