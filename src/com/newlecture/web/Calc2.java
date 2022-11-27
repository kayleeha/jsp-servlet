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
		ServletContext application = request.getServletContext(); // application�� �����ϰ� �ִ�..
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();
		
		response.setCharacterEncoding("UTF-8"); //�ѱ۷� ����Ϸ���
		response.setContentType("text/html; charset=UTF-8"); //�������� �ѱ۷� �ν������� ��û
		
		String v_ = request.getParameter("v");
		String op = request.getParameter("operator");
		
		int v = 0; //�⺻��
		
		if(!v_.equals("")) v = Integer.parseInt(v_);
		
		//���� ����ϴ� ��
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
			
			if(operator.equals("+"))   // input ���� submit�� ��� value ���� ���޵�.. �ٸ� �� name ���� ���޵���
				// �����ϴ� ���� "="�� �ƴϸ� setAttribute�� ���常 �Ѵ�.
				result = x + y;
			else 
				result = x - y; 
			
			response.getWriter().printf("result is %d\n", result);
			//����� ����ȴٴ� ���� application�� ����� ���� ������ Ȱ���� �� �ִٴ� ��
		}
		
//		application.setAttribute("value", v); 
//		application.setAttribute("op", op);
		
		
		// ���� �����ϴ� ��
		else {
//			application.setAttribute("value", v);  //������ �� �� �ִ� ����
//			application.setAttribute("op", op);
	
//			session.setAttribute("value", v); //session ������ ���� (�繰��)
//			session.setAttribute("op", op);
			
			Cookie valueCookie = new Cookie("value", String.valueOf(v));
			
			//cookie ������ �ݵ�� ���ڿ��� ������ �� �ִ�.
			//op ���� ���ڿ������� value ���� int�̹Ƿ� String.valueOf ����ؾ���
			
			
			Cookie opCookie = new Cookie("op", op);
			valueCookie.setPath("/calc2"); // ����ڿ��� � URL�� ������ ���ΰ�..�����ϴ� ��
			valueCookie.setMaxAge(24*60*60); // ��Ű ���� ���� �ð� ����
			opCookie.setPath("/calc2"); //"" �ȿ� �ִ� �ּҸ� ��û�� ���� cookie ���޵�
			response.addCookie(valueCookie); //Ŭ���̾�Ʈ���� �����ϸ� Ŭ���̾�Ʈ �ʿ��� ����
			response.addCookie(opCookie);
			
			response.sendRedirect("calc2.html"); // �ڷΰ��� ��ư �� ������ html ���� �ٽ� �ҷ���
			
			
		}
	}
		
		
	
	
	
}