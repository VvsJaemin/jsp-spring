package ch10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO {
	
	final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
	final String JDBC_URL = "jdbc:mysql://localhost/root?serverTimezone=UTC";
	
	// DB 연결을 가져오는 메서드, DBCP를 사용하는 것이 좋음
	
	public Connection open() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn  = DriverManager.getConnection(JDBC_URL, "root", "root");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return conn;
	}
	
	public void addNews(News n)throws Exception{
		Connection conn = open();
		
		
		String sql = "INSERT INTO news(title, img, date, content) values(?, ?, NOW(), ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		try {
			
		pstmt.setString(1, n.getTitle());
		pstmt.setString(2, n.getImg());
		pstmt.setString(3, n.getContent());
		pstmt.executeUpdate();
	
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			pstmt.close();
			conn.close();
		}
				
	}
	
	public List<News> getAll() throws Exception{
		Connection conn = open();
		List<News> newsList = new ArrayList<News>();
		
		String sql = "SELECT aid, title, NOW() as cdate from news";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		try {
			while(rs.next()) {
				News n  = new News();
				n.setAid(rs.getInt("aid"));
				n.setTitle(rs.getString("title"));
				n.setDate(rs.getString("cdate"));
				
				newsList.add(n);
			}
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return newsList;
	}
	
	public News getNews(int aid) throws SQLException {
		Connection conn = open();
		
		News n = new News();
		String sql = "select aid, titie, img, now() as cdate, content from news where aid = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		rs.next();
		
		try {
			n.setAid(rs.getInt("aid"));
			n.setTitle(rs.getString("title"));
			n.setImg(rs.getString("img"));
			n.setDate(rs.getString("cdate"));
			n.setContent(rs.getString("content"));
			n.setAid(rs.getInt("aid"));
			pstmt.executeQuery();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n;
	}
	
	public void delNews(int aid) throws SQLException{
		Connection conn = open();
		
		String sql = "delete from news where aid = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try {
			pstmt.setInt(1, aid);
			if(pstmt.executeUpdate()==0){
				throw new SQLException("DB 에러");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
