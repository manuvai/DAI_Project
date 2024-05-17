<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Objects"%>
<%! @SuppressWarnings("unchecked") %>

<%@ page import="models.Rayon" %>
<%@ page import="models.Article" %>
<%@ page import="models.Utilisateur" %>
<%@ page import="models.Magasin" %>
<%@ page import="java.util.List" %>
<%@ page import="repositories.ArticleRepository" %>
<!DOCTYPE html>
<html>
<head>

<link rel="icon" href="images/logo-supermarket.png" type="image/x-icon"> 
<link rel="stylesheet" type="text/css" href="css/header.css">
<link rel="stylesheet" type="text/css" href="css/validerPanier.css">
<script src="js/home.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<title>Valider Panier</title>

  <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/superMarket.css">
    <link rel="stylesheet" type="text/css" href="css/panier.css">
</head>
<script src="js/promotion.js"></script>
<body>
<%@ include file="../template/head.jsp" %>
<div>
<h1>Validation du panier</h1>
<%
    String magasinRetrait = (String) session.getAttribute("magasinRetrait");
	Double totalValidation = (Double) session.getAttribute("totalPanierValidation");
	String nombreArrondi = String.format("%.2f", totalValidation);
	Utilisateur user = (Utilisateur) session.getAttribute("user");

    if (magasinRetrait == null) {
%>
        <p>Vous n'avez pas encore choisi de magasin.</p>
<%
    }
%>

<form action="ChoisirCreneauServlet" method="post">
    <label for="magasin">Choisir un magasin :</label>
    <select name="magasin" id="magasin">
        <% 
            List<Magasin> tousMagasins = (List<Magasin>) session.getAttribute("tousMagasins");
            for (Magasin magasin : tousMagasins) { 
        %>
                <option value="<%= magasin.getCodeMagasin() %>"><%= magasin.getNomMagasin() %></option>
        <% 
            } 
        %>
    </select>
    <br>
    <label for="utiliserPoints">Utiliser les points de fidélité : (<span id="ptFidel"><%= user.getPtFidelite()%></span> pts)</label>
    <input type="checkbox" id="utiliserPoints" name="utiliserPoints" value="true">
    <br>
    <input type="submit" value="Choisir le creneau">
</form>
Total : <span id="totalPP" ><%= nombreArrondi%></span>€

<%@ include file="../template/footer.jsp" %>
</body>
</html>
<!-- jQuery and Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>



