<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="repositories.ArticleRepository"%>
<%@page import="models.Article"%>
<%@page import="models.Panier" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<% 
request.setAttribute("pageTitle", "Préparation de commandes"); 
request.setAttribute("isHeaderDisabled", false);

List<String> cssFiles = new ArrayList<>();
cssFiles.add("css/preparationcommandes.css");
request.setAttribute(AbstractServlet.CSS_FILES_KEY, cssFiles);

List<String> jsFiles = new ArrayList<>();
jsFiles.add("js/preparationdetail.js");
request.setAttribute(AbstractServlet.JS_FILES_KEY, jsFiles);

%>
<%@ include file="../template/start.jsp" %>
<body>
	<h1>Commande en cours</h1>
	<a id="a-accueil" href="./PreparationCommandesServlet">Retour aux commandes</a>
	<br>
	<%
		Panier panier = (Panier) request.getAttribute("panier");
		if (panier.getEtat() == models.Panier.Etat.VALIDEE) {
			
	%>
	<button id="bu-start">Commencer la préparation</button>
	<button id="bu-annuler">Annuler</button> 
	
	<%
		}
	%>
	
	<h3 id="affichageCommande"> Panier numéro <%= " " + request.getAttribute("idCommande") %></h3>
	<table class="blueTable">
	    <thead>
	        <tr>
		        <% if (panier.getEtat() == models.Panier.Etat.VALIDEE) { %>
	            <th>Validation</th>
	            <% } %>
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
			    	 <% if (panier.getEtat() == models.Panier.Etat.VALIDEE) { %>
			        <td><input type="checkbox" class="checkbox"></td>
			        <% } %>
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
	
	<% if (panier.getEtat() == models.Panier.Etat.VALIDEE) { %>
	<button id="bu-stop">Terminer la préparation</button>
	<%
		}
	%>
<div  class="spacer"> . </div>
</body>

<%@ include file="../template/end.jsp" %>
