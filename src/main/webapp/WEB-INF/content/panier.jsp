<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Objects"%>
<%! @SuppressWarnings("unchecked") %>

<%@ page import="models.Rayon" %>
<%@ page import="models.Article" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>

<link rel="icon" href="images/logo-supermarket.png" type="image/x-icon"> 
<link rel="stylesheet" type="text/css" href="css/header.css">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<title>Panier</title>

  <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/superMarket.css">
</head>
<script src="js/home.js"></script>
<body>
<%@ include file="../template/head.jsp" %>
<div>
Bonjour, voici votre panier
Vous avez actuellement <%= session.getAttribute("nbrArticleTotal") %> article(s)

<h1>Liste des Articles Ajoutés</h1>
    
    <% 
        List<String> numeros = (List<String>) session.getAttribute("numeros");
    %>
    
    <% if (numeros != null && !numeros.isEmpty()) { %>
        <ul>
            <% for (String numero : numeros) { %>
            <% if ((int) session.getAttribute(numero)>0) { %>
                <li>Id de l'article : <%= numero %>- Quantité : <%= session.getAttribute(numero) %></li>
            <% }} %>
        </ul>
    <% } else { %>
        <p>Aucun article n'a été ajouté à la liste.</p>
    <% } %>
</div>



<%@ include file="../template/footer.jsp" %>
</body>
</html>
<!-- jQuery and Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>



