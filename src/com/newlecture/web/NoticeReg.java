package com.newlecture.web;

import java.io.IOException;

import java.io.ObjectOutput;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/notice-reg")
public class NoticeReg extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8"); //한글로 쓰려고
		response.setContentType("text/html; charset=UTF-8"); //브라우저야 한글로 인식해줘라고 요청
//		request.setCharacterEncoding("UTF-8"); //사용자로부터 입력을 받을 때 한국어로 받기 위해서 써줘야 함
		
		PrintWriter out = response.getWriter(); //출력 도구
		
		
		String title = request.getParameter("title");  //cnt_는 임시 변수명임.. temp라고 생각하자
		String content = request.getParameter("content");  //html의 name 값을 키워드로 사용자가 입력하는 값을 받음
		
		out.print(title);
		out.print(content);
		
		
	}

}
