<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="springMVC/testMyView">test MyView</a>
	<br><br>

	<!-- 
		模拟修改操作
		1. 原始数据为：1，渣渣辉，123，12
		2. 密码不能被修改
	 -->
	<form action="springMVC/testModelAttribute" method="post">
		<input type="hidden" name="id" value="1">
		姓名：<input type="text" name="name" value="渣渣辉">
		<br>
		年龄：<input type="text" name="age" value="12">
		<br>
		<input type="submit" value="提交">
	</form>

	<a href="springMVC/testSessionAttributes">test SessionAttributes</a>
	<br><br>
	
	<a href="springMVC/testMap">test Map</a>
	
	<br><br>
	<a href="springMVC/testModelAndView">test ModelAndView</a>
	<br><br>

	<a href="springMVC/testServletAPI">test servletAPI</a>
	<br><br>
	
	<form action="springMVC/testPOJO" method="post">
		用户名：<input type="text" name="name"><br>
		密码：<input type="password" name="password"><br>
		年龄：<input type="text" name="age"><br>
		省份：<input type="text" name="address.province"><br>
		城市：<input type="text" name="address.city"><br>
		<input type="submit" value="提交">
	</form>

	<br>
	<a href="springMVC/testCookieValue">test cookieValue</a>

	<br>
	<a href="springMVC/testRequestHeader">test requestHeader</a>
	
	<br>
	<a href="springMVC/testRequestParam?name=克里斯&age=12">test RequestParam</a>

	<form action="springMVC/testRest/32" method="post">
		<input type="hidden" name="_method" value="PUT">
		<input type="submit" value="test put">
	</form>
	
	<br>
	
	<form action="springMVC/testRest/1234" method="post">
		<input type="hidden" value="DELETE" name="_method">
		<input type="submit" value="test delete">
	</form>
	<br>

	<form action="springMVC/testRest" method="post">
		<input type="submit" value="test post">
	</form>
	<br>

	<a href="springMVC/testRestGet/123">test restGet</a>
	<br>

	<a href="springMVC/testPathVariable/12">test pathVariable</a>
	
	<br>

	<a href="springMVC/testAntPath/2323/abc">test antPath</a>

	<br>

	<a href="springMVC/testParamAndHeader?name=cris&age=11">test paramsAndHeaders</a>
	
	<br>
	<form action="springMVC/testMethod" method="post">
		<input type="submit" value="提交">
	</form>
	
	<a href="springMVC/testMethod">test method</a>
	<br>
	<a href="springMVC/test">test requestMapping</a>
	<br>
	<a href="helloworld">test helloworld</a>


</body>
</html>