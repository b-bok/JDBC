package com.kh.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Member;

public class MemberDao {

	public int insertMember(Connection conn, Member m) {
		
		
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ? , SYSDATE)";
		
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
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
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
		
			JDBCTemplate.close(pstmt);
			
		}
		
		
		return result;
	}
	
	
	public ArrayList<Member> selectList(Connection conn) {
		
		ArrayList<Member> list = new ArrayList<Member>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;		
	
		String sql = "SELECT * FROM MEMBER ORDER BY USERNO DESC";
		
		
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
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
			
		}

		return list;
	}
	
	
	public Member selectByUserId(Connection conn, String userId) {
		
		Member m = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = ?";
		
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
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		

		return m;
		
		
		
	}


	public ArrayList<Member> selectByUserName(Connection conn, String keyword) {
		
		ArrayList<Member> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
		
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
			
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);

		}
		return list;
	}


	public int updateMember(Connection conn, Member m) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
	
		
		String sql = "UPDATE MEMBER SET USERPWD = ?, "
									  + "EMAIL = ?, "
									  + "PHONE = ?, "
									  + "ADDRESS = ? "
					+ "WHERE USERID = ?";
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			
			result = pstmt.executeUpdate();
			
			
			
			
			if(result > 0) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
		
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}


	public int deleteMember(Connection conn,String userId) {

		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USERID = ?";
		
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			}
					
			
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		
		
		
		return result;
	}
	
	
	

}
