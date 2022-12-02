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
		//request에 담은 model을 index.jsp에 forward 해서 request,response 공유
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String title = request.getParameter("title"); //jsp에서 제목에 해당하는 name 값이 title이므로 그 값을 넣어줌
		String content = request.getParameter("content");
		String isOpen = request.getParameter("open");

		Collection<Part> parts = request.getParts();
		StringBuilder builder = new StringBuilder(); //쉼표로 구분해서 파일을 넣기 위해서
				
		for(Part p : parts) {
			if(!p.getName().equals("file")) continue;

			Part filePart = p;
			
			String fileName = filePart.getSubmittedFileName();
			builder.append(fileName);
			builder.append(",");
			
			InputStream fis = filePart.getInputStream(); //InputStream을 통해서 얻어서 저장
			

			String realPath = request.getServletContext().getRealPath("/upload");
			System.out.println(realPath); // 사용자가 전달한 파일을 물리적인 곳..realPath에다 파일 저장

			String filePath = realPath + File.separator + fileName;
			FileOutputStream fos = new FileOutputStream(filePath);

			byte[] buf = new byte[1024]; // 읽은 걸 담아두기 위한 변수
			int size = 0;
			while((size = fis.read(buf)) != -1) //byte 단위로 한꺼번에 퍼올림
				fos.write(buf,0,size); // 1024를 항상 딱 맞춰서 사용하지 않으므로 딱 떨어지는 수까지 맞춰줌
			// 데이터를 byte 단위로 읽지만 정수형인 int 값 사용. read 하다가 다 읽으면 정수형으로 돌려줘야 함 
			// 읽다가 스트림 끝에 도달하면 -1이라는 정수 값을 반환한다.
			//읽어 온 갯수를 size 보고 알 수 있고 더 이상 읽어올 게 없으면 -1 반환

			fos.close();
			fis.close();

		}
		
		builder.delete(builder.length()-1, builder.length()); //구분자가 마지막에는 안 들어가게 하기 위해서 사용

		boolean pub = false; //체크가 되면 true
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
