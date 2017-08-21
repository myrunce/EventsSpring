<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
	fieldset{
		display:inline-block;
	}
</style>
</head>
<body>
	<fieldset class="addEvent">
    	<h3>Edit Event:</h3>
    	<p>
		<form:errors path="event.*" />
	</p>
	    <form:form method="POST" action="/${event.id}/edit" modelAttribute="event">
		  
		    <form:label path="name">Name of the Event:
		    <form:input path="name"/></form:label><br><br>
		    
		    <form:label path="date">Date
		    <form:input type="date" pattern="dd-MM-yyyy" path="date"/></form:label><br><br>
		    
		    <form:label path="city">City:
		    <form:input path="city"/></form:label><br><br>
		    
		    <form:label path="state">State:
		    <form:select path="state">
		    	<c:forEach items="${states}" var="state">
		    		<form:option value="${state}" label="state">${state}</form:option>
		    	</c:forEach>
		    </form:select>
		    </form:label><br><br>
			
		    <input type="submit" value="Edit Event"/>
		</form:form>
	</fieldset><BR><BR>
	<a href="/events">Back</a>
</body>
</html>