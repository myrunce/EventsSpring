<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
<style>
	.submitLink {
  		background-color: transparent;
  		text-decoration: underline;
 		border: none;
 		color: blue;
  		cursor: pointer;
		}
	submitLink:focus {
  			outline: none;
		}
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
		fieldset.eventTable{
			display:inline-block;
			width:800px;
		}
		fieldset.addEvent{
			display:inline-block;
		}
		h1.welcome{
			display:inline-block;
			margin-right:750px;
			
		}
		form.logoutForm{
			display:inline-block;		
		}
</style>
</head>
<body>
	<div class="header">
    <h1 class="welcome">Welcome <c:out value="${currentUser.firstName}"></c:out>!</h1>
    <form class="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" class="submitLink" value="Logout" />
    </form>
    </div>
    <p>Here are some events in your state:</p>
    <fieldset class="eventTable">
    	<table>
    		<thead>
    			<tr>
    				<th>Name</th>
    				<th>Date</th>
    				<th>Location</th>
    				<th>Host</th>
    				<th>Action/Status</th>
    			</tr>
    		</thead>
    		<tbody>
    		<c:forEach items="${eventsInState}" var="event">
    				<tr>
    					<td><a href="events/${event.id}">${event.name}</a></td>
    					<td><fmt:formatDate type = "date" dateStyle="LONG" value = "${event.date}"/></td>
    					<td>${event.city}</td>
    					<td>${event.host.firstName} ${event.host.lastName}</td>
    					<c:if test = "${event.host.id == currentUser.id}">
    						<td><a href="${event.id}/edit">Edit</a> <a href="${event.id}/delete">Delete</a></td>
    					</c:if>
    					<c:if test = "${event.peopleGoing.size() < 1 && event.host.id != currentUser.id }">
    							<td><a href="${event.id}/join">Join</a></td>
    						</c:if>
    					<c:forEach items="${event.peopleGoing}" var="people">
    						<c:if test = "${event.host.id != currentUser.id && people.id != currentUser.id}">
    							<td><a href="${event.id}/join">Join</a></td>
    						</c:if>
    						<c:if test = "${people.id == currentUser.id}">
    							<td>Joined <a href="${event.id}/cancel">Cancel</a></td>
    						</c:if>
    					</c:forEach>
    			</c:forEach>
    		</tbody>
    	</table>
    </fieldset>
    <p>Here are some of the events in other states:
    <fieldset class="eventTable">
    	<table>
    		<thead>
    			<tr>
    				<th>Name</th>
    				<th>Date</th>
    				<th>Location</th>
    				<th>Host</th>
    				<th>Action/Status</th>
    			</tr>
    		</thead>
    		<tbody>
    			<c:forEach items="${eventsOutOfState}" var="event">
    				<tr>
    					<td><a href="events/${event.id}">${event.name}</a></td>
    					<td><fmt:formatDate type = "date" dateStyle="LONG" value = "${event.date}"/></td>
    					<td>${event.city}</td>
    					<td>${event.host.firstName} ${event.host.lastName}</td>
    					<c:if test = "${event.host.id == currentUser.id}">
    						<td><a href="${event.id}/edit">Edit</a> <a href="${event.id}/delete">Delete</a></td>
    					</c:if>
    					<c:if test = "${event.peopleGoing.size() < 1 && event.host.id != currentUser.id }">
    							<td><a href="${event.id}/join">Join</a></td>
    						</c:if>
    					<c:forEach items="${event.peopleGoing}" var="people">
    						<c:if test = "${event.host.id != currentUser.id && people.id != currentUser.id}">
    							<td><a href="${event.id}/join">Join</a></td>
    						</c:if>
    						<c:if test = "${people.id == currentUser.id}">
    							<td>Joined <a href="${event.id}/cancel">Cancel</a></td>
    						</c:if>
    					</c:forEach>
    				</tr>
    			</c:forEach>
    		</tbody>
    	</table>
    </fieldset><br><br>
    <fieldset class="addEvent">
    	<h3>Add a new Event:</h3>
    	<p>
		<form:errors path="event.*" />
	</p>
	    <form:form method="POST" action="/events" modelAttribute="event">
		  
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
			
		    <input type="submit" value="Create an Event"/>
		</form:form>
	</fieldset>
</body>
</html>