<%@page import="repositories.PanierRepository, models.Panier, java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setAttribute("pageTitle", "preparationcommandes");


List<String> cssFiles = new ArrayList<>();
cssFiles.add("css/preparationcommandes.css");
request.setAttribute(AbstractServlet.CSS_FILES_KEY, cssFiles);
%>
<%@ include file="../template/start.jsp" %>
<h1>Page de préparation des commandes</h1> 

 
<table class="blueTable">
	<thead>
		<tr>
			<th>Commandes en cours</th>
			<th>Date de collecte</th>
		</tr>
	</thead>
	<tbody>
	    <% for (Panier panier : (List<Panier>) request.getAttribute("paniers")) {%>
	    	<tr>
		    	<td>
			    	<form action="./PreparationCommandeDetailServlet" method="get">
	    				<input type="hidden" name="idCommande" value="<%= panier.getId() %>">
	    				<input type="submit" value="<%= panier.getId() %>">
					</form>
		    	</td>
		    	<td><%= panier.getCreneau().getDateCreneau().toString() %> <br> <%= panier.getCreneau().getHeureCreneau().name().substring(1).replace("_", " à ") %> </td>
	    	</tr>
	    <% } %>
	</tbody>
</table>
	
<%@ include file="../template/end.jsp" %>
