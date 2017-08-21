<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
	table {
		border-collapse: collapse;
		width: 100%;
	}
		
	td, th {
		border: 1px solid #dddddd;
		text-align: left;
		padding: 8px;
	}
		
	tr:nth-child(even) {
		background-color: #dddddd;
	}
	fieldset.people{
		display:inline-block;
	}
	fieldseT.message{
		display:inline-block;
		width:400px;
		height:300px;
		overflow:scroll;
	}
</style>
</head>
<body>
	<h1>${event.name}</h1>
	<p>Host: ${event.host.firstName} ${event.host.lastName}<br>
	Date: <fmt:formatDate type = "date" dateStyle="LONG" value = "${event.date}"/><br>
	Location: ${event.city}, ${event.state} <br>
	People who are attending this event: ${event.peopleGoing.size()}</p>
	<fieldset class="people">
		<table>
			<thead>
				<tr>
					<th>Name</th>
					<th>Location</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${event.peopleGoing}" var="person">
					<tr>
						<td>${person.firstName} ${person.lastName}</td>
						<td>${person.city}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
	<div class="messages">
		<h1>Message Wall</h1>
		<fieldset class="message">
			<c:forEach items="${messages}" var="message">
				<p>${message.op.firstName} ${message.op.lastName} says: ${message.message}<p>
				<p>*************************************</p> 
			</c:forEach>
		</fieldset>
	</div>
	
	<div class="addComment">
		<h4>Add Comment:</h4>
		<p>
			<form:errors path="m.*"/>
		</p>
		<form:form method="POST" action="/events/${event.id}" modelAttribute="m">
		  
		    <form:label path="message">
		    <form:textarea path="message"/></form:label><br><br>
		    
			<input type="submit" value="comment"/>
		</form:form>
	</div><BR><BR>
	<a href="/events">Back</a>
</body>
</html>