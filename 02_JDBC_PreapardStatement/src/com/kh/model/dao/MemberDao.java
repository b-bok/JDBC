package com.kh.model.dao;

import java.sql.*;
import java.util.ArrayList;

import com.kh.model.vo.Member;

//DAO : DB와 직접 접근해서 sql 실행후 결과 받기(JDBC)

/*
 * * JDBC 처리 순서
 * 
 * 1) jdbc driver 등록(해당 DBMS에서 제공하는 ) 
 * 2) Connection 객체 생성 (DB와 연결정보를 담는 객체 == Connection) url, username, password
 * 3) [Prepared]Statement 객체 생성
 * 4) SQL문 실행 :  executeXXX(실행할 sql)	--> executeUpdate(dml문) : int  	/  executeQuery(select문) : ResultSet
 * 5) 결과 받기   (처리된 행수 int / 조회된 결과 ResultSet)
 * 		6_1) 조회 결과 ResultSet --> 조회 결과를 vo에 주섬주섬 담기 (한행 : vo 객체 한개	/ 여러행 : ArrayList<vo>)
 * 		6_2) 처리된 행수  int 	  --> 트랜잭션 처리 (commit, rollback)
 * 
 * 7) 다쓴자원반납 (close): 생성된 역순으로 
 */
public class MemberDao {

	
	/*
	 * 	* Statement 와 PreparedStatement의 차이점
	 *  - Statement 같은 경우 완성된  sql문을 바로 실행하는 객체(== sql문을 완성형태로!! == 사용자가 입력한 값으로 채워진 형태)
	 *  - PreparedStatement 같은 경우 "미완성된 sql문"을 잠시 보관해둘 수 있는 객체
	 *    해당 sql문 실행하기 전 완성형태로 만든 후 실행 하면됨!
	 *    
	 *     
	 *     * 기존의 Statement 방식
	 *     Connection 객체를 통해 Statement 객체 생성 : 		stmt = conn.createStatement();
	 *     Statememt  객체를 통해서 "완성된 sql문 " 실행 및 결과 받기 : 결과 = stmt.executeXXX(완성된sql문);
	 *     
	 *     
	 *     * PreparedStatement 방식
	 *     Connection 객체를 통해 PreparedStatement 객체 생성 : psmt = conn.prepareStatement(sql문);
	 *     해당 sql문 실행 전에 PreparedStatement에 담겨있는
	 *     				    미완성된 sql문 완성형태로 만들기!!    : psmt.setXXX(1,"대체할값"); pstmt.setXXX(2,"대체할 값");
	 *     해당 완성된 sql문 실행 및 결과 받기					: 결과  = pstmt.executeXXX(); 
	 * 
	 */
	
	
	public int insertMember(Member m) {	// insert문 ==> 처리된 행 갯수(int) => 트랜잭션 처리
		
		int result = 0;	// 처리된 결과(처리된 행수)를 담은 변수
		Connection conn = null;	// DB의 연결 정보를 담은 객체
		PreparedStatement pstmt = null;	
		
		// 실행하고자 하는 sql문 미완성 형태로 둘 수 있음!!
		// 미리 사용자가 입력한 값들이 들어갈 공간 확보! ?(홀더)를 사용!
		String sql =  "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?,SYSDATE)";
		
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
			pstmt = conn.prepareStatement(sql);	// PreparedStatement 객체 생성시 sql 문 담은채 생성!
			
			// 현재 pstmt에 담긴 sql문이 미완성된 상태이기 때문에 바로 실행 불가!! --> 각각의 공간에 들어갈 실제값(사용자 입력값) 대체한 후 실행
			// pstmt.setXXX(홀더 순번, 대체값);
			// pstmt.setString(홀더 순번, 대체할 값);		--> '대체할 값' (양옆에 홑따옴표 붙여서 들어감!)
			// pstmt.setInt(홀더 순번, 대체할 값);		--> 대체 할 값	
			
			
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9,m.getHobby());
			
			// pstmt의 최대 단점 : 완성된 sql문의 형태를 미리 볼 수 없음!
			
			// 4,5) sql문 실행 및 결과 받기
			result = pstmt.executeUpdate();
			
			// 6_2)
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				// 7)
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
		
		
	}
	
	
	public ArrayList<Member> selectList() {// select문 => ResultSet ==> 여러행
		
		ArrayList<Member> list = new ArrayList<Member>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER";
		
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:Xe","JDBC","JDBC");
			
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			
			while(rset.next()) {
				Member m = new Member();
				
				m.setUserNo(rset.getInt(1));
				m.setUserId(rset.getString(2));
				m.setUserPwd(rset.getString(3));
				m.setUserName(rset.getString(4));
				m.setGender(rset.getString(5));
				m.setAge(rset.getInt(6));
				m.setEmail(rset.getString(7));
				m.setPhone(rset.getString(8));
				m.setAddress(rset.getString(9));
				m.setHobby(rset.getString(10));
				m.setEnrollDate(rset.getDate(11));
				
				list.add(m);
				
				
			}
			
			
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
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
	
	
	public Member selectByUserId(String userId) {
		
		Member m = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "SELECT * FROM MEMBER WHERE USERID = ? ";
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member(rset.getInt(1)
							 , rset.getString(2)
							 , rset.getString(3)
							 , rset.getString(4)
							 , rset.getString(5)
							 , rset.getInt(6)
							 , rset.getString(7)
							 , rset.getString(8)
							 , rset.getString(9)
							 , rset.getString(10)
							 , rset.getDate(11));
			}
			
		} catch (ClassNotFoundException e) {


			e.printStackTrace();
		} catch (SQLException e) {
		
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
				
			} catch (SQLException e) {
		
				e.printStackTrace();
			}
		}
		
		return m;
		
	}
	
	public ArrayList<Member> selectByUserName(String userName) {
		
		ArrayList<Member> list = new ArrayList<Member>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%' || ? || '%'";
		
		try {
			

				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");

				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, userName);
				
				rset = pstmt.executeQuery();
				
				while(rset.next()) {
					
					list.add(new Member(rset.getInt("USERNO")
							, rset.getString("USERID")
							, rset.getString("USERPWD")
							, rset.getString("USERNAME")
							, rset.getString("GENDER")
							, rset.getInt("AGE")
							, rset.getString("EMAIL")
							, rset.getString("PHONE")
							, rset.getString("ADDRESS")
							, rset.getString("HOBBY")
							, rset.getDate("ENROLLDATE")
							)
					);
				}
				
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
		
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		
		return list;
			
	}
	
	public int updateMember(Member m) {// update문 => 처리된 행수 => 트랜잭션
		
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
	
		
		String sql = "UPDATE MEMBER SET USERPWD = ? "
				+ ", EMAIL = ?"
				+ ", PHONE = ?"
				+ ", ADDRESS = ?"
				+ "WHERE USERID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		}
		
		return result;
		
	}


	public int deleteMember(String userId) {//delete문 => 행 횟수 => 트랜잭션처리
		
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USERID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
			
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
			
		
		return result;
	}
	
}
	

