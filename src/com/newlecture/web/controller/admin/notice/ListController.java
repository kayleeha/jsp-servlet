package com.newlecture.web.controller.admin.notice;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.NoticeView;
import com.newlecture.web.service.NoticeService;


@WebServlet("/admin/board/notice/list")
public class ListController extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String[] openIds = request.getParameterValues("open-id");
		String[] delIds = request.getParameterValues("del-id");
		String cmd = request.getParameter("cmd");
		
		switch(cmd) {
		case "일괄공개":
			
			for(String openId : openIds)
				System.out.printf("open id : %s\n", openId);
			break;
			
		case "일괄삭제":
			
			for(String delId : delIds)
				System.out.printf("del id : %s\n", delId);
			
			NoticeService service = new NoticeService(); // 실제 삭제 실행되는 곳은 데이터베이스임. controller 몫이 아님.
			int[] ids = new int[delIds.length];
			for(int i=0; i<delIds.length; i++) // 원래 id는 정수형 변수
				ids[i] = Integer.parseInt(delIds[i]);
			
			int result = service.deleteNoticeAll(ids); //삭제가 잘 됐는지 알려주기 위해 결과값 설정
			break;
		}
		
		response.sendRedirect("list"); // controller는 입출력만 담당하니까 삭제 실행 후 목록 페이지 보여주면 됨
		//get 메서드를 요청해서 RequestDispatcher 사용할 수 있게 만드는 것
	
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//list?f=title&q=a

		String field_ = request.getParameter("f");
		String query_ = request.getParameter("q");
		String page_ = request.getParameter("p");

		//int는 null을 받을 수 있는 정수형이 아니다.

		String field = "title";
		if(field_ != null && !field_.equals("")) 
			field = field_;

		String query = "";
		if(query_ != null && !query_.equals("")) 
			query = query_;

		int page = 1;
		if(page_ != null && !page_.equals("")) 
			page = Integer.parseInt(page_);



		NoticeService service = new NoticeService();
		List<NoticeView> list = service.getNoticeList(field, query, page);
		int count = service.getNoticeCount(field, query);


		request.setAttribute("list", list);
		request.setAttribute("count", count);

		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/list.jsp").forward(request, response);
		//request에 담은 model을 detail.jsp에 forward 해서 request,response 공유


	}
}
