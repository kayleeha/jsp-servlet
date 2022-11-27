package com.newlecture.web;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/calc2")
public class Calc2 extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext application = request.getServletContext(); // application을 저장하고 있는..
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();
		
		response.setCharacterEncoding("UTF-8"); //한글로 출력하려고
		response.setContentType("text/html; charset=UTF-8"); //브라우저야 한글로 인식해줘라고 요청
		
		String v_ = request.getParameter("v");
		String op = request.getParameter("operator");
		
		int v = 0; //기본값
		
		if(!v_.equals("")) v = Integer.parseInt(v_);
		
		//값을 계산하는 것
		if(op.equals("=")) {
			
			//int x = (Integer)application.getAttribute("value");
			//int x = (Integer)session.getAttribute("value");
			int x = 0;
			for(Cookie c: cookies) 
			if(c.getName().equals("value")) {
				x = Integer.parseInt(c.getValue());
				break;
			}
			
			int y = v;
			
			//String operator = (String)application.getAttribute("op");
			//String operator = (String)session.getAttribute("op");
			
			String operator = "";
			for(Cookie c: cookies) 
				if(c.getName().equals("op")) {
					operator = c.getValue();
					break;
				}
		
		
			
			
			int result = 0;
			
			if(operator.equals("+"))   // input 값이 submit인 경우 value 값이 전달됨.. 다른 건 name 값이 전달됐음
				// 전달하는 값이 "="이 아니면 setAttribute에 저장만 한다.
				result = x + y;
			else 
				result = x - y; 
			
			response.getWriter().printf("result is %d\n", result);
			//결과가 실행된다는 말은 application에 저장된 값을 꺼내서 활용할 수 있다는 말
		}
		
//		application.setAttribute("value", v); 
//		application.setAttribute("op", op);
		
		
		// 값을 저장하는 것
		else {
//			application.setAttribute("value", v);  //누구나 쓸 수 있는 공간
//			application.setAttribute("op", op);
	
//			session.setAttribute("value", v); //session 공간이 있음 (사물함)
//			session.setAttribute("op", op);
			
			Cookie valueCookie = new Cookie("value", String.valueOf(v));
			
			//cookie 값에는 반드시 문자열만 저장할 수 있다.
			//op 값은 문자열이지만 value 값은 int이므로 String.valueOf 사용해야함
			
			
			Cookie opCookie = new Cookie("op", op);
			valueCookie.setPath("/calc2"); // 사용자에게 어떤 URL을 전달할 것인가..제한하는 것
			valueCookie.setMaxAge(24*60*60); // 쿠키 값의 만료 시간 설정
			opCookie.setPath("/calc2"); //"" 안에 있는 주소를 요청할 때만 cookie 전달됨
			response.addCookie(valueCookie); //클라이언트에게 전달하면 클라이언트 쪽에서 저장
			response.addCookie(opCookie);
			
			response.sendRedirect("calc2.html"); // 뒤로가기 버튼 안 누르고 html 파일 다시 불러옴
			
			
		}
	}
		
		
	
	
	
}