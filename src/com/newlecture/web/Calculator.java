package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calculator")
public class Calculator extends HttpServlet{
//	@Override
//	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		if(request.getMethod().equals("GET")) { //반드시 get을 "GET"(대문자)으로 작성할 것
//			System.out.println("GET 요청이 왔습니다.");
//	}
// 
//		else if(request.getMethod().equals("POST")) {
//			System.out.println("POST 요청이 왔습니다.");
//		}
//		
//		super.service(request, response); // service 호출하지 않으면 알아서 doPost 메소드가 호출된다.
//}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies(); //쿠키를 배열로 가져오는 작업
		
		String exp = "0";
		if(cookies != null)
			for(Cookie c: cookies) 
				if(c.getName().equals("exp")) {
					exp = c.getValue();
					break;
			} //쿠키를 읽어오는 작업
		
		response.setCharacterEncoding("UTF-8"); //한글로 출력하려고
		response.setContentType("text/html; charset=UTF-8"); //브라우저야 한글로 인식해줘라고 요청
		PrintWriter out = response.getWriter();
		
		out.write("<!DOCTYPE html>");
		out.write("<html>");
		out.write("<head>");
		out.write("<meta charset=\"UTF-8\">");
		out.write("<title>Insert title here</title>");
		out.write("<style>");
		out.write("input{");
		out.write("width:50px;");
		out.write("height:50px;");
		out.write("}");

		out.write(".output{");
		out.write("	height:50px;");
		out.write("	background: #e9e9e9;");
		out.write("	font-size: 24px;");
		out.write("font-weight:bold;");
		out.write("	text-align:right;");
		out.write("	padding:0px 5px;");
		out.write("}");
		out.write("</style>");
		out.write("</head>");
		out.write("<body>");
		out.write("<form method=\"post\">"); //get 요청을 하는 CalPage와 post를 요청하는 Calc3의 url이 다를 때는 action 값을 써줘야 하지만 url이 현재 페이지라면 action 값이 필요없음
		out.write("	<table>");
		out.write("	<tr>");
					out.printf("		<td class=\"output\" colspan=\"4\">%s</td>", exp);
					out.write("	<tr>");
					out.write("		<tr>");
					out.write("		<td><input type=\"submit\" name=\"operator\" value=\"CE\"></td>");
					out.write("		<td><input type=\"submit\" name=\"operator\" value=\"C\"></td>");
					out.write("		<td><input type=\"submit\" name=\"operator\" value=\"BS\"></td>");
					out.write("	<td><input type=\"submit\" name=\"operator\" value=\"/\"></td>");
					out.write("	<tr>");
					out.write("	<tr>");
					out.write("	<td><input type=\"submit\" name=\"value\" value=\"7\"></td>");
					out.write("	<td><input type=\"submit\" name=\"value\" value=\"8\"></td>");
					out.write("<td><input type=\"submit\" name=\"value\" value=\"9\"></td>");
					out.write("<td><input type=\"submit\" name=\"operator\" value=\"*\"></td>");
					out.write("<tr>");
					out.write("	<tr>");
						out.write("	<td><input type=\"submit\" name=\"value\" value=\"4\"></td>");
						out.write("	<td><input type=\"submit\" name=\"value\" value=\"5\"></td>");
						out.write("	<td><input type=\"submit\" name=\"value\" value=\"6\"></td>");
						out.write("	<td><input type=\"submit\" name=\"operator\" value=\"-\"></td>");
						out.write("<tr>");
						out.write("<tr>");
						out.write("<td><input type=\"submit\" name=\"value\" value=\"1\"></td>");
						out.write("<td><input type=\"submit\" name=\"value\" value=\"2\"></td>");
						out.write("<td><input type=\"submit\" name=\"value\" value=\"3\"></td>");
						out.write("<td><input type=\"submit\" name=\"operator\" value=\"+\"></td>");
						out.write("<tr>");
						out.write("	<tr>");
						out.write("<td><input type=\"submit\" name=\"value\" value=\"0\"></td>");
						out.write("	<td><input type=\"submit\" name=\"dot\" value=\".\"></td>");
						out.write("	<td><input type=\"submit\" name=\"operator\" value=\"=\"></td>");
						out.write("<tr>");
						out.write("</table>");
				
						out.write("</body>");
						out.write("</html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		expCookie.setPath("/calculator"); // /calculator 이외에 다른 url에는 cookie가 전달되지 않음
		response.addCookie(expCookie);
		response.sendRedirect("calculator"); // 자기를 불러옴
		
			
			
		//사용자가 전달하는 값을 가지고 exp을 만들어서 cookie에 저장을 하고 calcPage에 전달함.
	}
}