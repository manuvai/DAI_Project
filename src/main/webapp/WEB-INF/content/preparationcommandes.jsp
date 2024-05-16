<%@page import="repositories.PanierRepository, models.Panier, java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "preparationcommandes"); %>

<%@ include file="../template/head.jsp" %>
<link rel="stylesheet" type="text/css" href="css/header.css">
<link rel="stylesheet" type="text/css" href="css/footer.css">
<link rel="stylesheet" type="text/css" href="css/preparationcommandes.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

</body>
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
</body>
	
<%@ include file="../template/footer.jsp" %>
