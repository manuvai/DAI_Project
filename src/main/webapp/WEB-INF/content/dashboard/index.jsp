<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!@SuppressWarnings("unchecked")%>

<%@page import="java.util.Objects"%>
<%@ page import="models.Article"%>
<%@ page import="java.util.List"%>
<%
List<String> jsFiles = new ArrayList<>();
jsFiles.add("js/home.js");
request.setAttribute(AbstractServlet.JS_FILES_KEY, jsFiles);

%>

<%@ include file="../../template/start.jsp"%>

<h2>Articles commandés le plus fréquemment</h2>
<% if (request.getAttribute("articlesCaroussel") == null || ((List<Article>) request.getAttribute("articlesCaroussel")).isEmpty()) { %>
	<div class="container">
		Cette liste est vide. <a class="text-mute" href="<%= request.getContextPath() %>/home">Voir quoi ajouter ?</a>
	</div>
<% } else { %>
<%@ include file="../commons/article_caroussel.jsp"%>
<% } %>
<%@ include file="../../template/end.jsp"%>
