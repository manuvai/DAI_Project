<%@page import="java.util.Optional"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="servlets.AbstractServlet"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
Map<String, String> messagesKeyMap = new HashMap<>();
messagesKeyMap.put(AbstractServlet.ERRORS_KEY, "danger");
messagesKeyMap.put(AbstractServlet.SUCCESSES_KEY, "success");
messagesKeyMap.put(AbstractServlet.INFOS_KEY, "primary");

Map<String, List<String>> messagesMap = new HashMap<>();
messagesKeyMap.forEach((key, value) -> messagesMap.put(key, (List<String>) request.getAttribute(key)));

for (Entry<String, List<String>> entry : messagesMap.entrySet()) {
	String key = entry.getKey();
	String alertType = Optional.of(messagesKeyMap.get(key)).orElse("primary");
	List<String> messageList = entry.getValue();
	
	if (messageList != null && messageList.size() > 0) {
%>
	<div class="alert alert-<%= alertType %>" role="alert">
		<%= String.join("\n<hr/>", messageList) %>
	</div>
<%
	}
			
}
%>
