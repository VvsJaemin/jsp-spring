package ch09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

public class StudentDAO {
	Connection conn = null;
	PreparedStatement pstmt;
	
	final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
	final String JDBC_URL = "jdbc:mysql://localhost/root?serverTimezone=UTC";
	
	// 연결 종료 메서드 구현
	public void open() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, "root", "root");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// 학생 등록 메서드 구현
	public void insert(Student s) {
		open();
		String sql = "INSERT INTO student(username, univ, birth, email) values (?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,s.getUsername());
			pstmt.setString(2,s.getUniv());
			pstmt.setDate(3, s.getBitrh());
			pstmt.setString(4,s.getEmail());
			
			pstmt.execute();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally {
			close();
		}
	}
	
	// 학생 목록 메서드 구현
	public List<Student> getAll(){
		open();
		List<Student> students = new ArrayList();
		
		try {
			pstmt = conn.prepareStatement("select * from student");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Student s = new Student();
				s.setId(rs.getInt("id"));
				s.setUsername(rs.getString("username"));
				s.setUniv(rs.getString("univ"));
				s.setBitrh(rs.getDate("birth"));
				s.setEmail(rs.getString("email"));
				
				students.add(s);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			close();
		}
		return students;
	}
}
