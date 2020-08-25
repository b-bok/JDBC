package com.kh.common;

import java.sql.*;

public class JDBCTemplate {

	// 이 클래스에서 모든 메소드들 다 static 메소드로 작업!
	// 싱글톤 패턴 : 메모리 영역에 단 한번만 올리고, 재사용하는 개념!
	
	// 1. Connection 객체 생성해서 (즉, DB연결 된) 반환시켜주는 메소드
	public static Connection getConnection() {
		
		Connection conn = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
	// 2. 전달 받은 Connection  객체를 가지고 commit 시켜주는 메소드
	public static void commit(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	// 3. 전달 받은 Connection 객체를 가지고  rollback 시켜주는 메소드
	public static void rollback(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	// 4. 전달 받은 Connection 객체 close해주는 메소드
	public static void close(Connection conn) {
		
		try {
			if(conn != null  && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	// 5. 전달받은 Statement 객체들 close해주는 메소드
	public static void close(Statement stmt) {
		
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}
	
	// 6. 전달 받은 ResultSet 객체 close해주는 메소드
	public static void close (ResultSet rset) {
		
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
}
