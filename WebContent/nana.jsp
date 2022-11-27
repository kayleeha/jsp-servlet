<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

/*response.setCharacterEncoding("UTF-8"); // 위의 지시자에서 이미 설정했으므로 필요없음
response.setContentType("text/html; charset=UTF-8"); // */

//PrintWriter out = response.getWriter(); //out이라는 내장 객체가 있으므로 필요없음


String cnt_ = request.getParameter("cnt");  //cnt_는 임시 변수명임.. temp라고 생각하자

int cnt = 100; //기본값
if(cnt_ != null && !cnt_.equals(""))
	cnt = Integer.parseInt(cnt_); // 널
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%for(int i=0; i<cnt; i++)  {%>
	안녕 Servlet!!<br>
	<%} %>
</body>
</html>