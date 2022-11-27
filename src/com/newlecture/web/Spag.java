package com.newlecture.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/spag") //controller에서 실행해야 한다. ctrl + F11
public class Spag extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num = 0; // 사용자가 'n'에 아무것도 입력하지 않았을 경우에 기본값
		String num_ = request.getParameter("n"); //임시 변수
		if(num_ != null && !num_.equals(""))
			num = Integer.parseInt(num_);
		
		String result; //mvc 중 model에 해당함
		
		
		if(num%2 !=0) 
			result = "홀수";
		
		else 
			result = "짝수";
		
		request.setAttribute("result", result);
		
		String[] names = {"aaa","bbb"};
		request.setAttribute("names", names);
		
		Map<String, Object> notice = new HashMap<String, Object>();
		notice.put("id", 1);
		notice.put("title", "EL은 좋아요");
		request.setAttribute("notice", notice);


		
		RequestDispatcher dispatcher = request.getRequestDispatcher("spag.jsp");
		dispatcher.forward(request, response); //forward 관계에 있는, 공유할 수 있는 저장소는 request가 사용된다.
		//spag.jsp와 연결해줌 
	}
}
