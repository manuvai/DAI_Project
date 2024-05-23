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
	<h1>Commande en cours</h1>
	<a id="a-historique" href="./PreparationCommandesServlet">Retour aux commandes</a>
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
	            <th class="colonneFine">Id</th>
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
			            boolean isQuantiteInitialised = false;
			%>
			    <tr>
			    	 <% if (panier.getEtat() == models.Panier.Etat.VALIDEE) { %>
			        <td><input type="checkbox" class="checkbox"></td>
			        <% } %>
			        <td class="colonneFine" id="idArticle<%=article.getId()%>"><%= article.getId() %></td>
			        <td><%= article.getDesc() %></td>
			        <td>
			        	<div class="editPreparateur">
			        		<% if (panier.getEtat() == models.Panier.Etat.VALIDEE) { %>
			        			<i id="enleverButton" class="boutonPanier fas fa-minus icon" onclick="enleverArticle(<%= article.getId() %>, <%= request.getAttribute("idCommande")%>)" title="moins" id="moins"></i>
			        		<%} %>	
			        			<% if (!isQuantiteInitialised) { %> 
			        				<span id="quantite<%=article.getId()%>"><%=quantite %></span>
			        				<% isQuantiteInitialised = true; %>
			        			<% } else { %>
			        				<span id="quantite<%=article.getId()%>"></span>
			        			<% } %>
			        		<% if (panier.getEtat() == models.Panier.Etat.VALIDEE) { %>
			        			<i id="ajouterButton"  class="boutonPanier fas fa-plus icon" onclick="ajouterArticle(<%= article.getId() %>, <%= request.getAttribute("idCommande")%>)" title="plus" id="plus"></i>
			        		<% } %>
			        	</div>
			        </td>
			        <td><%= quantite %></td><td><%= String.format("%.2f", article.getPrixUnitaire() * quantite * (1 + article.getPromotion() / 100.0)) %>€</td>
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

<%@ include file="../template/end.jsp" %>
