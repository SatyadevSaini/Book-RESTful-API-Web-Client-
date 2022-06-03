<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
	<h1> ${appName} </h1>
	<hr>
	<a href="/">Home</a>
	<hr>
	<h3>Add Book</h3>
	<p style='background-color:yellow;width:max-content;'> ${addResult} </p>
	<form action="addBook" method="post" enctype="multipart/form-data">
		Book Name: <input type="text" name="name" required/><br/><br/>
		Book Price: <input type="number" name="price" required/><br/><br/>
		Author Name: <input type="text" name="aname" required/><br/><br/>
		Publisher Name: <input type="text" name="pname" required/><br/><br/>
		Book Photo: <input type="file" name="image1" required/><br/><br/>
		<input type="submit" value="Add Book"/>
	</form>
	<hr/>
	<h3>All Books</h3>
	<p style='background-color:yellow;width:max-content;'> ${allBooks} </p>
	
	<a href="allBooks">All Books</a>
	
	<form action="allBooks" method="get">
		<input type="submit" value="All Books"/>
	</form>
</body>
</html>