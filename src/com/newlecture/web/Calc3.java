package com.newlecture.web;

import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/calc3")
public class Calc3 extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Cookie[] cookies = request.getCookies(); //쿠키를 배열로 가져오는 작업
		
//		response.setCharacterEncoding("UTF-8"); //한글로 출력하려고
//		response.setContentType("text/html; charset=UTF-8"); //브라우저야 한글로 인식해줘라고 요청
		// 계산기 입력만 받지 출력할 일 없으니 사용하지 않음
		
		String value = request.getParameter("value"); //사용자가 입력한 내용
		String operator = request.getParameter("operator"); //calcPage의 input name 값이 옴
		String dot = request.getParameter("dot");
		
		String exp = "";
		if(cookies != null)
			for(Cookie c: cookies) 
				if(c.getName().equals("exp")) {
					exp = c.getValue();
					break;
			} //쿠키를 읽어오는 작업
		
		if(operator != null && operator.equals("=") ) { // "=" 연산자는 계산을 하는 연산자가 아니므로 따로 뺌
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
			try {
				exp = String.valueOf(engine.eval(exp));
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
		
		else if(operator != null && operator.equals("C") ) { //'C' 클릭하면 계산기 초기화
			exp = "";
		
		}
		
		else {
			
			exp += (value == null)? "" : value; 
			exp += (operator == null)? "" : operator;
			exp += (dot == null)? "" : dot;
		
			}
		
		Cookie expCookie = new Cookie("exp", exp);
		if(operator != null && operator.equals("C") ) 
			expCookie.setMaxAge(0); // 계산기 초기화 할 때 쿠키를 아예 삭제하고 싶을 때 만료 시간을 0으로 만들어줌
		
		expCookie.setPath("/");
		response.addCookie(expCookie);
		response.sendRedirect("calcpage"); // 뒤로가기 버튼 안 누르고 html 파일 다시 불러옴
		
			
			
		//사용자가 전달하는 값을 가지고 exp을 만들어서 cookie에 저장을 하고 calcPage에 전달함.
		}
	}
		
		
	
	
	
