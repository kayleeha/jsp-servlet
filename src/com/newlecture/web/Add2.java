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
		response.setCharacterEncoding("UTF-8"); //한글로 쓰려고
		response.setContentType("text/html; charset=UTF-8"); //브라우저야 한글로 인식해줘라고 요청
		
		String[] num_ = request.getParameterValues("num");
		
		int result = 0;
		
		for(int i = 0; i< num_.length; i++) {
			int num = Integer.parseInt(num_[i]); //연산은 반복되지만 선언은 반복되지 않으므로 for문 안에 변수 선언 가능
			result += num;
		}
		
		
		response.getWriter().printf("result is %d\n", result);
	
	
	}
	
	
}
