<%@page import="repositories.ArticleRepository, models.Article, java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "preparationcommandes"); %>

<%@ include file="../template/head.jsp" %>
<link rel="stylesheet" type="text/css" href="css/preparationcommandes.css">

<h1>Commande en cours</h1>
 
	<table class="blueTable">
	    <thead>
	        <tr>
	            <th>Validation</th>
	            <th>Nom Article</th>
	            <th>Quantité</th>
	            <th>Prix</th>
	        </tr>
	    </thead>
	    <tbody>
	        <tr>
	            <td><input type="checkbox"></td>
	            <td>Nom de l'article 1</td>
	            <td>1</td>
	            <td>10€</td>
	        </tr>
	        <tr>
	            <td><input type="checkbox"></td>
	            <td>Nom de l'article 2</td>
	            <td>2</td>
	            <td>20€</td>
	        </tr>
	        
	      	<% for (Article article : (List<Article>) request.getAttribute("articles")) {%>
	    	<tr>
		    	<td><input type="checkbox"></td>
	            <td><%= article.getDesc() %></td>
	            <td>1</td>
	            <td>10</td>
	    	</tr>
	    <% } %>
	        
	        
	    </tbody>
	</table>
	
<%@ include file="../template/tail.jsp" %>
