package com.kh.model.service;

import java.sql.*;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

// Service : DB와 연결시키는 Connection 객체 생성후
//			 Dao 호출(이때, 여기서 생성된 conn과 전달값을 같이 넘겨줄 것임! )
//			 반환받은 결과 (만일, result가 반환 될 때 트랜잭션 처리도!)
public class MemberService {

	public int insertMember(Member m) {
		
		// 필요한 변수 셋팅
		/*
		 * int result = 0; Connection conn = null;
		 * 
		 * 
		 * try {
		 * 
		 * Class.forName("oracle.jdbc.driver.OracleDriver");
		 * 
		 * conn =
		 * DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC",
		 * "JDBC");
		 * 
		 * result = new MemberDao().insertMember(conn, m);
		 * 
		 * 
		 * 
		 * } catch (ClassNotFoundException e) {
		 * 
		 * e.printStackTrace(); } catch (SQLException e) {
		 * 
		 * e.printStackTrace(); }
		 */
		
		Connection conn = JDBCTemplate.getConnection();
		int result = new MemberDao().insertMember(conn, m);
		
		
		/*
		 * //트랜잭션 처리 if(result > 0) { try { if(conn != null && !conn.isClosed()) {
		 * conn.commit(); } } catch (SQLException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * }else { try { if(conn != null && !conn.isClosed()) { conn.rollback(); } }
		 * catch (SQLException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * }
		 */
		
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		
		
		// 다쓴 자원 반납
		/*
		 * try { if(conn != null && !conn.isClosed()) { conn.close(); } } catch
		 * (SQLException e) {
		 * 
		 * e.printStackTrace(); }
		 */
		
		JDBCTemplate.close(conn);

		
		return result;
		}
	
	
	public ArrayList<Member> selectList() {
		
		ArrayList<Member> list = new ArrayList<Member>();
		
		Connection conn = JDBCTemplate.getConnection();
		
		list = new MemberDao().selectList(conn);
		
		JDBCTemplate.close(conn);
		
		return list;
		
	}
	
	public Member selectByUserId(String userId) {
		
		
		Member m = null;
		
		Connection conn = JDBCTemplate.getConnection();
		
		m = new MemberDao().selectByUserId(conn,userId);
		
		JDBCTemplate.close(conn);
		
		
		return m;
	}


	public ArrayList<Member> selectByUserName(String keyword) {
		
		ArrayList<Member> list = new ArrayList<>();
		
		Connection conn = JDBCTemplate.getConnection();
		
		list = new MemberDao().selectByUserName(conn, keyword);
		
		
		JDBCTemplate.close(conn);
		
		
		return list;
	}


	public int updateMember(Member m) {
		
		int result = 0;
		
		Connection conn = JDBCTemplate.getConnection();
		
		result = new MemberDao().updateMember(conn, m);
		
		JDBCTemplate.close(conn);
		
		
		return result;
	}


	public int deleteMember(String userId) {
		int result = 0;
		
		Connection conn = JDBCTemplate.getConnection();
		
		result = new MemberDao().deleteMember(conn, userId);
		
		
		JDBCTemplate.close(conn);
		
		
		
		return result;
	}
	
	
	

}
