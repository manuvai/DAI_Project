<%@page import="java.util.ArrayList"%>
<%@page import="servlets.AbstractServlet"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% 
List<String> errors = (List<String>) request.getAttribute(AbstractServlet.ERRORS_KEY);
if (errors != null && errors.size() > 0) {
%>
	<div class="alert alert-danger" role="alert">
		<%= String.join("\n<hr/>", errors) %>
	</div>
<% 
}
%>
