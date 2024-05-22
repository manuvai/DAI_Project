<%@page import="repositories.PanierRepository, models.Panier, java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setAttribute("pageTitle", "preparationcommandes");
request.setAttribute("isHeaderDisabled", false);

List<String> cssFiles = new ArrayList<>();
cssFiles.add("css/preparationcommandes.css");
request.setAttribute(AbstractServlet.CSS_FILES_KEY, cssFiles);
%>
<%@ include file="../template/start.jsp" %>
<body>
	<h1>Historique des commandes préparées</h1> 
	
	 
	<table class="blueTable">
		<thead>
			<tr>
				<th>Historique des commandes préparées</th>
				<th>Date de collecte</th>
				<th>Statut</th>
			</tr>
		</thead>
		<tbody>
		    <% for (Panier panier : (List<Panier>) request.getAttribute("paniers")) {%>
		    	<tr>
			    	<td>
				    	<form action="./PreparationCommandeDetailServlet" method="get">
		    				<input type="hidden" name="idCommande" value="<%= panier.getId() %>">
		    				<input type="submit" id="bt-panierSelector" value="<%= panier.getId() %>">
						</form>
			    	</td>
			    	<td><%= panier.getCreneau().getDateCreneau().toString() %> <br> <%= panier.getCreneau().getHeureCreneau().name().substring(1).replace("_", " à ") %> </td>
			    	<td><%= panier.getEtat().toString() %>
		    	</tr>
		    <% } %>
		</tbody>
	</table>
	
	<a id="a-accueil" href="./PreparationCommandesServlet">Retour aux commandes</a>
	
<%@ include file="../template/end.jsp" %>
