<%@page import="servlets.AbstractServlet"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="images/logo-supermarket.png" type="image/x-icon"> 
<title>
<%= request.getAttribute("pageTitle") == null 
	? "Online Shop" 
	: request.getAttribute("pageTitle") %>
</title>
<!-- All libs -->
<%
/*
    Cette partie ajoute les librairies css au template général
*/
List<String> _cssLibs = (List<String>) request.getAttribute(AbstractServlet.CSS_LIBS_KEY);

_cssLibs = _cssLibs == null
    ? new ArrayList<>()
    : _cssLibs;

_cssLibs.add("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css");
_cssLibs.add("https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css");

for (String cssLib : _cssLibs) {
%>
<link 
    rel="stylesheet" 
    href="<%= cssLib %>">
<%
}

/*
    Cette partie ajoute du css au template général
*/
List<String> _cssFiles = (List<String>) request.getAttribute(AbstractServlet.CSS_FILES_KEY);

_cssFiles = _cssFiles == null
    ? new ArrayList<>()
    : _cssFiles;

_cssFiles.add("css/superMarket.css");
_cssFiles.add("css/header.css");
_cssFiles.add("css/footer.css");

for (String cssFile : _cssFiles) {
%>
<link 
    rel="stylesheet"
    type="text/css"
    href="<%= request.getContextPath() + "/" + cssFile %>">
<%
}
%>
</head>
<body>

<% 
Boolean isHeaderDisabled = (Boolean) request.getAttribute("isHeaderDisabled");
if (isHeaderDisabled == null || Boolean.FALSE.equals(isHeaderDisabled)) {
%>
<%@include file="head.jsp" %>
<%
}
%>

<div class="container">
<%@include file="messages.jsp" %>
