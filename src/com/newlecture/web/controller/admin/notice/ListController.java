package com.newlecture.web.controller.admin.notice;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
		
		String[] openIds = request.getParameterValues("open-id"); //����ڰ� üũ�� ���̵� //3,5,8
		String[] delIds = request.getParameterValues("del-id");
		String cmd = request.getParameter("cmd");
		String ids_ = request.getParameter("ids");
		String[] ids = ids_.trim().split(" "); //�Խ� ��Ͽ� �־��� ��� ���̵� 1,2,3,4,5,6,7,8,9,10
			//���Ŀ� ���� ���鼭 ó��
		
		NoticeService service = new NoticeService(); // ���� ���� ����Ǵ� ���� �����ͺ��̽���. controller ���� �ƴ�.
		
		switch(cmd) {
		case "�ϰ�����":
			
			for(String openId : openIds)
				System.out.printf("open id : %s\n", openId);
			
			List<String> oids = Arrays.asList(openIds); //Arrays.asList (�迭�� list ���·� �ٲ��ִ� �Լ�)
			
			List<String> opnIds = Arrays.asList(openIds);
			//1,2,3,4,5,6,7,8,9,10 - //3,5,8
			//1,2,4,,6,7,9,10
			List<String> cids = new ArrayList(Arrays.asList(ids));
			cids.removeAll(oids);
			
		
			service.pubNoticeAll(oids, cids);
			//service.CloseNoticeList(clsIds); // transaction ó���� ���ؼ� �� ���� ó��
			
			break;
			
		case "�ϰ�����":
			
			for(String delId : delIds)
				System.out.printf("del id : %s\n", delId);
			
			int[] ids1 = new int[delIds.length];
			for(int i=0; i<delIds.length; i++) // ���� id�� ������ ����
				ids1[i] = Integer.parseInt(delIds[i]);
			
			int result = service.deleteNoticeAll(ids1); //������ �� �ƴ��� �˷��ֱ� ���� ����� ����
			break;
		}
		
		response.sendRedirect("list"); // controller�� ����¸� ����ϴϱ� ���� ���� �� ��� ������ �����ָ� ��
		//get �޼��带 ��û�ؼ� RequestDispatcher ����� �� �ְ� ����� ��
	
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//list?f=title&q=a

		String field_ = request.getParameter("f");
		String query_ = request.getParameter("q");
		String page_ = request.getParameter("p");

		//int�� null�� ���� �� �ִ� �������� �ƴϴ�.

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
		//request�� ���� model�� detail.jsp�� forward �ؼ� request,response ����


	}
}
