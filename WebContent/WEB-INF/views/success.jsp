<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>success jsp</h3>

	date:${requestScope.date }
	<br><br>
	
	cars:${requestScope.cars }
	<br><br>
	
	request user:${requestScope.user }
	<br><br>
	session user:${sessionScope.user }
	<br><br>
	request school:${requestScope.school }
	<br><br>
	session school:${sessionScope.school }
	<br><br>
	request user:${requestScope.user }
	<br><br>
	request abc:${requestScope.abc }
	<br><br>
	
	<fmt:message key="i18n.name"></fmt:message>
	<br><br>
	<fmt:message key="i18n.password"></fmt:message>
	<br><br>
	
	
	
</body>
</html>