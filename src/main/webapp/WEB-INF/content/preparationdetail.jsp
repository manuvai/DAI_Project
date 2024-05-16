<%@page import="repositories.ArticleRepository, models.Article, java.util.List, java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "preparationcommandes"); %>

<%@ include file="../template/head.jsp" %>
<link rel="stylesheet" type="text/css" href="css/preparationcommandes.css">

<body>
	<h1>Commande en cours</h1>
	 
	 	<button id="bu-start">Commencer la préparation</button>
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
				<%
				    Map<Article, Integer> mapArticles = (Map<Article, Integer>) request.getAttribute("articlesQte");
				    if (mapArticles != null) {
				        for (Map.Entry<Article, Integer> entry : mapArticles.entrySet()) {
				            Article article = entry.getKey();
				            int quantite = entry.getValue();
				%>
				    <tr>
				        <td><input type="checkbox" class="checkbox"></td>
				        <td><%= article.getDesc() %></td>
				        <td><%= quantite %></td>
				        <td><%= article.getPrixUnitaire()*quantite %>€</td>
				    </tr>
				<%
				        }
				    }
				%>
		    
		    </tbody>
		</table>
		<button id="bu-stop">Terminer la préparation</button>
		<script src="js/preparationdetail.js"></script>
</body>
<%@ include file="../template/footer.jsp" %>