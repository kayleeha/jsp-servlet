package com.newlecture.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;

public class NoticeService {
	public List<Notice> getNoticeList() {
		
		return getNoticeList("title", "", 1);
		
	}
	public List<Notice> getNoticeList(int page) {
		
		return getNoticeList("title", "", page);
		
	}
	public List<Notice> getNoticeList(String field, String query, int page) {
		
		List<Notice> list = new ArrayList<>();
		
		
		String sql = "SELECT * FROM ("
				+ " SELECT ROWNUM NUM, N.* "
				+ " FROM (SELECT * FROM NOTICE WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N"
				+ " )"
				+ " WHERE NUM BETWEEN ? AND ?";
		
	// 1, 11, 21, 31 -> an = 1+(page-1)*10
	// 10, 20, 30, 40 -> page*10
		
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
	
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "c##newlecture", "12345");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,"%" + query + "%");
			st.setInt(2,1+(page-1)*10);
			st.setInt(3,page*10);
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");  // 변수가 model이 됨 //view로 넘거야 함(detail.jsp로 전달)
				String writerId = rs.getString("WRITER_ID");
				Date regDate = rs.getDate("REGDATE");
				String hit = rs.getString("HIT"); 
				String files = rs.getString("FILES"); 
				String content = rs.getString("CONTENT");
				

			Notice notice = new Notice(
							id,
							title,
							writerId,
							regDate,
							hit,
							files,
							content
						
					);
			
				list.add(notice);
			}
			
		    rs.close();
		    st.close();
		    con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return list;
		
	}
	
	public int getNoticeCount() {
		
		return getNoticeCount("title", "");
	}
	public int getNoticeCount(String feild, String query) {
		
		int count = 0;
		
		String sql = "SELECT * FROM ("
				+ " SELECT ROWNUM NUM, N.* "
				+ " FROM (SELECT * FROM NOTICE WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N" +
				")";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "c##newlecture", "12345");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,"%" + query + "%");
			st.setInt(2,1+(page-1)*10);
			st.setInt(3,page*10);
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");  // 변수가 model이 됨 //view로 넘거야 함(detail.jsp로 전달)
				String writerId = rs.getString("WRITER_ID");
				Date regDate = rs.getDate("REGDATE");
				String hit = rs.getString("HIT"); 
				String files = rs.getString("FILES"); 
				String content = rs.getString("CONTENT");
				

			Notice notice = new Notice(
							id,
							title,
							writerId,
							regDate,
							hit,
							files,
							content
						
					);
			
				list.add(notice);
			}
			
		    rs.close();
		    st.close();
		    con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	
	public Notice getNotice(int id) {
		
		String sql = "SELECT * FROM NOTICE WHERE ID=?";
		
		return null;
	}
	public Notice getNextNotice(int id) {

		String sql = "SELECT ID FROM (SELECT * FROM NOTICE ORDER BY REGDATE ASC)"
				+ " WHERE REGDATE > (SELECT REGDATE FROM NOTICE WHERE ID=3)"
				+ " AND ROWNUM = 1)";
		
		return null;
		
	}
	
	public Notice getPrevNotice(int id) {
		
		String sql = "SELECT ID FROM (SELECT * FROM NOTICE ORDER BY REGDATE DESC)"
				+ " WHERE REGDATE < (SELECT REGDATE FROM NOTICE WHERE ID=3)"
				+ " AND ROWNUM = 1";
		
		return null;
		
	}
	
	
	
	
	
	
}
