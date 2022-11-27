package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hi")
public class Nana extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8"); //한글로 출력하려고
		response.setContentType("text/html; charset=UTF-8"); //브라우저야 한글로 인식해줘라고 요청
		PrintWriter out = response.getWriter();
		
		
		String cnt_ = request.getParameter("cnt");  //cnt_는 임시 변수명임.. temp라고 생각하자
		
		int cnt = 100; //기본값
		if(cnt_ != null && !cnt_.equals(""))
			cnt = Integer.parseInt(cnt_); // 널이나 빈문자 아니면 임시 변수 값을 cnt에 넣겠다. 
		
		
		
		for(int i=0; i<cnt; i++) 
			out.println((i+1) + " : 안녕 Servlet!!<br>");
		
		
	}

}
