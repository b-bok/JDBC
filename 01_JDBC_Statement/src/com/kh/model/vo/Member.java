package com.kh.model.vo;

import java.sql.Date;

/*
 * 	* VO (Value Object)
 * 	  DB 테이블 한 행의 데이터를 기록하는 저장용 객체
 * 	  
 *  * 유사용어
 *    DTO (Data Transfer Object)
 *    DO  (Domain Object)
 *    Entity (--> Strut에서 이 용어 사용)
 *    bean (--> EJB에서 사용)
 *    
 *   * VO 조건
 *   1) 반드시 캡슐화 적용할 것
 *   2) 기본 생성자 및 매개변수 생성자 
 *   3) 모든 필드에 대한 setter/getter 메소드 작성
 *    
 */

public class Member {

	// 필드는 DB컬럼 정보와 동일하게 작업
	
	private int userNo;
	private String userId;
	private String userPwd;
	private String userName;
	private String gender;
	private int age;
	private String email;
	private String phone;
	private String address;
	private String hobby;
	private Date enrollDate;	// java.sql.Date import!
	
	
	// 기본 생성자, 매개변수 생성자, getter/setter, toString
	
	public Member() {};
	
	// o
	public Member(int userNo, String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String hobby, Date enrollDate) {
		
		this.userNo = userNo;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
		this.enrollDate = enrollDate;
	}
	
	

	public Member(String userId, String userPwd, String userName, String gender, int age, String email, String phone,
			String address, String hobby) {
		
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
	}

	// s, r
	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	
	//s
	@Override
	public String toString() {
		return userNo + ", " + userId + ", " + userPwd + ", " + userName
				+ ", " + gender + ", " + age + ", " + email + ", " + phone + ", "
				+ address + ", " + hobby + ", " + enrollDate;
	}
	
	
	
	
	
	
}
