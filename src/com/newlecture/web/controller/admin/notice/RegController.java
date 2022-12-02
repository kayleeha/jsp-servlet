package com.newlecture.web.controller.admin.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.catalina.ha.backend.CollectedInfo;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

@MultipartConfig(
		fileSizeThreshold = 1024*1024,
		maxFileSize = 1024*1024*50,
		maxRequestSize = 1024*1024*50*5

		)
@WebServlet("/admin/board/notice/reg")
public class RegController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp").forward(request, response);
		//request�� ���� model�� index.jsp�� forward �ؼ� request,response ����
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String title = request.getParameter("title"); //jsp���� ���� �ش��ϴ� name ���� title�̹Ƿ� �� ���� �־���
		String content = request.getParameter("content");
		String isOpen = request.getParameter("open");

		Collection<Part> parts = request.getParts();
		StringBuilder builder = new StringBuilder(); //��ǥ�� �����ؼ� ������ �ֱ� ���ؼ�
				
		for(Part p : parts) {
			if(!p.getName().equals("file")) continue;

			Part filePart = p;
			
			String fileName = filePart.getSubmittedFileName();
			builder.append(fileName);
			builder.append(",");
			
			InputStream fis = filePart.getInputStream(); //InputStream�� ���ؼ� �� ����
			

			String realPath = request.getServletContext().getRealPath("/upload");
			System.out.println(realPath); // ����ڰ� ������ ������ �������� ��..realPath���� ���� ����

			String filePath = realPath + File.separator + fileName;
			FileOutputStream fos = new FileOutputStream(filePath);

			byte[] buf = new byte[1024]; // ���� �� ��Ƶα� ���� ����
			int size = 0;
			while((size = fis.read(buf)) != -1) //byte ������ �Ѳ����� �ۿø�
				fos.write(buf,0,size); // 1024�� �׻� �� ���缭 ������� �����Ƿ� �� �������� ������ ������
			// �����͸� byte ������ ������ �������� int �� ���. read �ϴٰ� �� ������ ���������� ������� �� 
			// �дٰ� ��Ʈ�� ���� �����ϸ� -1�̶�� ���� ���� ��ȯ�Ѵ�.
			//�о� �� ������ size ���� �� �� �ְ� �� �̻� �о�� �� ������ -1 ��ȯ

			fos.close();
			fis.close();

		}
		
		builder.delete(builder.length()-1, builder.length()); //�����ڰ� ���������� �� ���� �ϱ� ���ؼ� ���

		boolean pub = false; //üũ�� �Ǹ� true
		if(isOpen != null)
			pub = true;

		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
		notice.setWriterId("yellow");
		notice.setFiles(builder.toString());

		NoticeService service = new NoticeService();
		service.insertNotice(notice);

		response.sendRedirect("list");
	}

}
