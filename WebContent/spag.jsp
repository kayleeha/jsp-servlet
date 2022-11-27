<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
	pageContext.setAttribute("result", "hello");
%>
<body>
<!-- 	<%= request.getAttribute("result")%>입니다. -->
	${requestScope.result} <br> <!--requestScope는 session객체가 아니라 한정사임 -->
	${names[0]}<br>
	${notice.id}<br>
	${result}<br>
	${empty param.n?'값이 비어 있습니다.' : param.n}<br>
	${header.accept} <!-- 사용자가 request할 때 보내온 header 정보 -->
</body>
</html>