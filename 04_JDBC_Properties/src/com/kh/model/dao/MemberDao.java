package com.kh.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.model.vo.Member;

import static com.kh.common.JDBCTemplate.*;
public class MemberDao {

	/*
	 * 기존의 방식 :  DAO 클래스에 사용자가 요청할 때마다 실행햐아되는 sql문을 자바소스코드 내에 명시적으로 작성 => 정적 코딩 방식
	 * 	>문제점 :  SQL문을 수정해야될 경우 자바 소스 코드를 수정 할 셈! => 수정된 내용을 바녕하고자 한다면 프로그램 재 구동 (프로그램 비정상적 종료 후 재구동)
	 * 			 => 유지보수 불편
	 * 
	 * 	해결방식 : SQL 문들을 별도로 관리하는 외부 파일 (.proerties)로 만들어서 사용자가 서비스를 요청할 때 마다
	 * 			실시간으로 이 파일에 기록되어 있는 sql문을 동적으로 읽어들여서 실행		=> 동적 코딩 방식
	 * 
	 */
	
	private Properties prop = new Properties();	//비어있는 상태
	
	// 기본 생성자 명시 이유 => 사용자가 서비스 요청시 매번 new MemberDao().xxx();
	// 즉, 기본 생성자 매번 호출 하도록!
	public MemberDao() {
		
		try {
			prop.load(new FileReader("resources/query.properties"));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	public int insertMember(Connection conn, Member m) {
		
		
		int result = 0;
		PreparedStatement pstmt = null;
		//String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ? , SYSDATE)";
		String sql = prop.getProperty("insertMember");
		
		
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,  m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			// 실행
			result = pstmt.executeUpdate();
			
			
			if(result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			/*
			 * try { if(pstmt != null && !pstmt.isClosed()) { // NullPointException 방지 차원
			 * pstmt.close(); } } catch (SQLException e) {
			 * 
			 * e.printStackTrace(); }
			 * 
			 */
		
			close(pstmt);
			
		}
		
		
		return result;
	}
	
	
	public ArrayList<Member> selectList(Connection conn) {
		
		ArrayList<Member> list = new ArrayList<Member>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;		
	
		//String sql = "SELECT * FROM MEMBER ORDER BY USERNO ASC";
		String sql = prop.getProperty("selectList");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Member(rset.getInt("userno")
								  , rset.getString("userid")
								  , rset.getString("userpwd")
								  , rset.getString("username")
								  , rset.getString("gender")
								  , rset.getInt("age")
								  , rset.getString("email")
								  , rset.getString("phone")
								  , rset.getString("address")
								  , rset.getString("hobby")
								  , rset.getDate("enrolldate"))
						);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			
		}

		return list;
	}
	
	
	public Member selectByUserId(Connection conn, String userId) {
		
		Member m = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//String sql = "SELECT * FROM MEMBER WHERE USERID = ?";
		String sql = prop.getProperty("selectByUserId");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member(rset.getInt("userno")
							 , rset.getString("userid")
							 , rset.getString("userpwd")
							 , rset.getString("username")
							 , rset.getString("gender")
							 , rset.getInt("age")
							 , rset.getString("email")
							 , rset.getString("phone")
							 , rset.getString("address")
							 , rset.getString("hobby")
							 , rset.getDate("enrolldate")
						       );
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		

		return m;
		
		
		
	}


	public ArrayList<Member> selectByUserName(Connection conn, String keyword) {
		
		ArrayList<Member> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		//String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
		String sql = prop.getProperty("selectByUserName");
		
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%" + keyword + "%");
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				list.add(new Member(rset.getInt("userno")
								  , rset.getString("userid")
								  , rset.getString("userpwd")
								  , rset.getString("username")
								  , rset.getString("gender")
								  , rset.getInt("age")
								  , rset.getString("email")
								  , rset.getString("phone")
								  , rset.getString("address")
								  , rset.getString("hobby")
								  , rset.getDate("enrolldate"))
						);
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			close(rset);
			close(pstmt);

		}
		return list;
	}


	public int updateMember(Connection conn, Member m) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
	
		
		/*
		 * String sql = "UPDATE MEMBER SET USERPWD = ?, " + "EMAIL = ?, " +
		 * "PHONE = ?, " + "ADDRESS = ? " + "WHERE USERID = ?";
		 */
		String sql = prop.getProperty("updateMember");
		
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			
			result = pstmt.executeUpdate();
			
			
			
			
			if(result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
		
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}


	public int deleteMember(Connection conn,String userId) {

		int result = 0;
		
		PreparedStatement pstmt = null;
		
		//String sql = "DELETE FROM MEMBER WHERE USERID = ?";
		String sql = prop.getProperty("deleteMember");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
					
			
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		
		
		
		return result;
	}


	public String loginMember(Connection conn,String userId, String userPwd) {
		// select문 => ResultSet
		
		String userName = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("loginMember");
		
		try {
			
			pstmt = conn.prepareStatement(sql);	// 미완성 형태
			
			// 완성형태!
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				
				userName = rset.getString("username");
			}
			

		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			close(rset);
			close(pstmt);
	
		}
		
		
		
		
		
		return userName;
	}
	
	
	

}
