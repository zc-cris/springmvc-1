<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

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