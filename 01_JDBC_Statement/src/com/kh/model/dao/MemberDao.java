package com.kh.model.dao;

import java.sql.*;
import java.util.ArrayList;

import com.kh.model.vo.Member;


// Dao (Data Acess Object) : DB에 직접 접근하는 담당(접근해서 sql문 실행 및 결과 받기)
public class MemberDao {

	
	/*
	 * 	* JDBC용 객체
	 *  - Connection : DB의 연결정보를 담고 있는 객체
	 *  - [Prepared]Statement : DB에 SQL문 전달해서 실행하고 그 결과 받아내는 객체
	 *  - ResultSet : SELECT문 수행 후 조회된 결과들이 담겨있는 객체
	 *  
	 *  * JDBC 처리순서
	 *  1) jdbc driver 등록  	 : 해당 DBMS가 제공하는 클래스 등록
	 *  2) Connection 객체 생성 : 연결하고자 하는 DB정보 입력
	 *  3) Statement  생성 	 : Connection 객체를 이용해서 생성
	 *  4) sql문 전달하면서 실행    : Statement 객체를 이용해서 sql문 실행
	 *  5) 결과 받기 			 : 바로 결과 받기
	 *  	> SELECT문 --> ResultSet객체  (조회된 데이터)	--> 6_1)
	 *  	> 	DML 문 --> int (처리된 행 수)			--> 6_2)
	 *  
	 *  6_1)	ResultSet에 담긴 데이터를 하나씩 뽑아서 vo객체에 담아준다.
	 *  6_2) 	트랜잭션 처리(성공이면 commit, 실패이면 rollback;)
	 *  
	 *  7) 다 쓴 JDBC용 객체 반드시 자원 반납(close)	--> 생성된 역순으로 반납
	 *  
	 */ 
	
	
	/**
	 * 사용자가 입력한 값들로 insert문 실행하는 메소드
	 * @param m	--> 사용자가 입력한 값들이 잔뜩 담겨 있는 Member 객체
	 */
	public int insertMember(Member m) {// insert문	=> 처리된 행 수	=> 트랜잭션 처리
		
		// 필요한 변수 먼저 셋팅
		
		int result = 0; // 처리된 결과 (처리된 행수 )를 받아줄 변수
		Connection conn = null; // DB에 연결정보 담는 객체
		Statement stmt = null;  // sql문 실행하고 결과를 받는 객체
		
		// 실행할 sql문 (완성형태로 만들어 둘것!)	--> 끝에 세미콜론 있으면 안됨!!
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, " 
												+ "'" + m.getUserId() + "', "
												+ "'" + m.getUserPwd() +"', " 
												+ "'" + m.getUserName()+ "', "
												+ "'" + m.getGender() + "', "
												+ 	  + m.getAge()    + ", "
												+ "'" + m.getEmail() + "', "
												+ "'" + m.getPhone() + "', "
												+ "'" + m.getAddress() + "', "
												+ "'" + m.getHobby() + "', SYSDATE)";
		//System.out.println(sql);
		
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver"); // ojdbc6.jar 파일 추가되어있는지, 오타 없는지
			
			// 2) Connection 객체 생성 (DB에연결 --> url, 계정명, 계정비밀번호)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4, 5) DB에 SQL문 전달 하면서 실행 후 결과 받기 (처리된 행 수)
			result = stmt.executeUpdate(sql);	// 반드시 result에 담아줘야 성공, 실패 출력 가능하다!!
			
			// 6_2) 트랜잭션 처리
			if(result > 0) {	// 성공했을 경우
				conn.commit();
				
				System.out.println("성공!");
			}else {				// 실패했을 경우
				conn.rollback();
				
				System.out.println("실패....");
			}
			
			
		} catch (ClassNotFoundException e) {
	
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			// 7) 다쓴 JDBC용 객체 자원 반납 (close)	--> 생성된 역순
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			

		}
	
		return result;
	
		
		
	}
	
	public ArrayList<Member> selectList() {//select문	=> ResultSet객체로 받음! (여러행)		
		
		// 필요한 변수 셋팅
		// 처리된 결과 (조회된 회원들(여러회원)	== 여러행)들을 담아줄 ArrayList생성!
		ArrayList<Member> list = new ArrayList<>();
		
		Connection conn = null;	// DB 연결정보 담는 객체
		Statement stmt = null;	// SQL문 실행 및 결과 받는 객체
		ResultSet rset = null;	// SELECT문 실행시 조회 결과값 담는 객체
		
		String sql = "SELECT * FROM MEMBER";
		
		
		try {
			//1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2) Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
			//3) Statement 객체 생성
			stmt = conn.createStatement();
			
			//4, 5) SQL문 전달해서 실행 결과 받기! (ResultSet 객체)
			rset = stmt.executeQuery(sql);	// select문은 executeQuery 메소드 사용!
											// dml문(insert, update, delete)은 executeUpdate 메소드 사용!
			
			
			
			//6_1
			while(rset.next()) {
				
				
				// 현재 rset 커서가 가리키고 있는 한 행 데이터 싹 뽑아서 Member 객체에 담기!
				Member m = new Member();
				// rset으로부터 어떤 컬럼에 해당하는 값을 뽑을 건지 제시!! (컬럼명!! 대소문자 가리지 않음!)
				m.setUserNo(rset.getInt("USERNO"));
				m.setUserId(rset.getString("USERID"));
				m.setUserPwd(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
				
				// 한 행에 대해 한 Member객체애 담는것 끝!
				list .add(m);	// 리스트에 해당 회원 객채 차곡차곡 담기
				
			}
			
			
			
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			//7) 다 쓴 자원 반납
			try {
				rset.close();
				stmt.close();
				conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
		
		
	}

}
