<%@page import="servlets.AbstractServlet"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% if (request.getAttribute("errors") != null && ((List<String>) request.getAttribute(AbstractServlet.ERRORS_KEY)).size() > 0) { %>
	<div class="errors">
		<ul>
			<% for (String error : (List<String>) request.getAttribute(AbstractServlet.ERRORS_KEY)) { %>
			<li><%= error %></li>
			<% } %>
		</ul>
	</div>
<% } %>
