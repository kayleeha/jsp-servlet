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
		
		response.setCharacterEncoding("UTF-8"); //�ѱ۷� ������
		response.setContentType("text/html; charset=UTF-8"); //�������� �ѱ۷� �ν������� ��û
//		request.setCharacterEncoding("UTF-8"); //����ڷκ��� �Է��� ���� �� �ѱ���� �ޱ� ���ؼ� ����� ��
		
		PrintWriter out = response.getWriter(); //��� ����
		
		
		String title = request.getParameter("title");  //cnt_�� �ӽ� ��������.. temp��� ��������
		String content = request.getParameter("content");  //html�� name ���� Ű����� ����ڰ� �Է��ϴ� ���� ����
		
		out.print(title);
		out.print(content);
		
		
	}

}
