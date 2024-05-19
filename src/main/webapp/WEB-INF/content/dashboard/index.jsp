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
<%@ include file="../commons/article_caroussel.jsp"%>

<%@ include file="../../template/end.jsp"%>
