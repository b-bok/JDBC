package com.kh.model.dao;

import java.sql.*;

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

}
