package com.newlecture.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*") //WebFilter 쓰면 request.setCharacterEncoding("UTF-8"); 파일마다 쓰지 않고도 브라우저에 한글 요청 가능
public class CharacterEncodingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");
		
		chain.doFilter(request, response);

	}

}
