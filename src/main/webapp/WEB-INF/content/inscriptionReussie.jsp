<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setAttribute("pageTitle", "Inscription réussie");
%>
<%@ include file="../template/start.jsp" %>
	<h1>Inscription réussie !</h1>
	<p>Votre inscription s'est bien déroulée, <strong><%= session.getAttribute("prenom") %> <%= session.getAttribute("nom") %></strong>. Bienvenue sur notre site !</p>
	<p><a href="home">Allez sur la page d'accueil</a></p>
<%@ include file="../template/end.jsp" %>