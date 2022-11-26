package com.newlecture.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/add2")
public class Add2 extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8"); //�ѱ۷� ������
		response.setContentType("text/html; charset=UTF-8"); //�������� �ѱ۷� �ν������� ��û
		
		String[] num_ = request.getParameterValues("num");
		
		int result = 0;
		
		for(int i = 0; i< num_.length; i++) {
			int num = Integer.parseInt(num_[i]); //������ �ݺ������� ������ �ݺ����� �����Ƿ� for�� �ȿ� ���� ���� ����
			result += num;
		}
		
		
		response.getWriter().printf("result is %d\n", result);
	
	
	}
	
	
}
