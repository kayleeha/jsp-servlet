package com.newlecture.web.controller;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.Notice;

@WebServlet("/notice/detail")
public class NoticeDetailController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String sql = "SELECT * FROM NOTICE WHERE id=?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "c##newlecture", "12345");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();

			rs.next();
		
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
		
		request.setAttribute("n", notice);
//		
//		request.setAttribute("title", title);  //위 model을 forward 하기 전에 request에 저장
//		request.setAttribute("writerId", writerId);
//		request.setAttribute("regDate", regDate);
//		request.setAttribute("hit",hit);
//		request.setAttribute("files", files);
//		request.setAttribute("content", content);

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
		

		request.getRequestDispatcher("/WEB-INF/view/notice/detail.jsp").forward(request, response);
		//request에 담은 model을 detail.jsp에 forward 해서 request,response 공유
		
	}

}
