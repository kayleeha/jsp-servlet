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
		
		response.setCharacterEncoding("UTF-8"); //�ѱ۷� ����Ϸ���
		response.setContentType("text/html; charset=UTF-8"); //�������� �ѱ۷� �ν������� ��û
		PrintWriter out = response.getWriter();
		
		
		String cnt_ = request.getParameter("cnt");  //cnt_�� �ӽ� ��������.. temp��� ��������
		
		int cnt = 100; //�⺻��
		if(cnt_ != null && !cnt_.equals(""))
			cnt = Integer.parseInt(cnt_); // ���̳� ���� �ƴϸ� �ӽ� ���� ���� cnt�� �ְڴ�. 
		
		
		
		for(int i=0; i<cnt; i++) 
			out.println((i+1) + " : �ȳ� Servlet!!<br>");
		
		
	}

}
