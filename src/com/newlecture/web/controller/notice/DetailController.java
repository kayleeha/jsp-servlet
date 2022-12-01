package com.newlecture.web.controller.notice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

@WebServlet("/notice/detail")
public class DetailController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		NoticeService service = new NoticeService(); //controller는 입출력 담당, 데이터베이스는 service가 담당
		Notice notice = service.getNotice(id);
		request.setAttribute("n", notice);

		request.getRequestDispatcher("/WEB-INF/view/notice/detail.jsp").forward(request, response);
		//request에 담은 model을 detail.jsp에 forward 해서 request,response 공유
		
	}

}
