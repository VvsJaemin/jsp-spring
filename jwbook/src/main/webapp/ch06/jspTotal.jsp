<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>JSP ���� ����</h2>
	<%!
		String[] members = {"��浿", "ȫ�浿", "����", "�ڻ��"};
		int num1 = 10;
		
		int calc(int num2){
			return num1 + num2;
		}
	%>
	
	<h3>
		2. calc(10) �޼��� ���� ��� :
		<%=calc(10) %>
	</h3>
	<hr>
	
	<h3>
		3. include :hello.jsp
	</h3>
	<%@include file="../hello.jsp" %>
</body>
</html>