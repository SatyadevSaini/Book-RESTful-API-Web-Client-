<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1> ${appName} </h1>
	<hr/>
	<a href="/">Home</a>
	<hr>
	<h3>Books</h3>
	
	<p style='background-color:yellow;width:max-content;'> ${updateResult} </p>
	
	  <c:forEach items="${allBooks}" var="b">
  		<div style="border:1px solid blue;width:max-content;padding:10px">
  			<p> <c:out value="${b.name}" /> </p>
  			<p> <c:out value="${b.price}" /> </p>
  			<p> <c:out value="${b.aname}" /> </p>
  			<p> <c:out value="${b.panme}" /> </p>
  			<img src="getPhoto?name=${b.name}" height="100px"/>
  			
  			<form action="updateImage" method="post" enctype="multipart/form-data">
  				<input type="hidden" name="name" value="${b.name}" />
				Book Photo: <input type="file" name="image1" required/><br/><br/>
				<input type="submit" value="Update Image"/>
			</form>
  			
  		</div>
	    <br/>  
	  </c:forEach>
</body>
</html>