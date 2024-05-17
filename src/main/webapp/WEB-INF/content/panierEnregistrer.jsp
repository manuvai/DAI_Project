<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Objects"%>
<%! @SuppressWarnings("unchecked") %>

<%@ page import="models.Rayon" %>
<%@ page import="models.Article" %>
<%@ page import="models.Utilisateur" %>
<%@ page import="models.Magasin" %>
<%@ page import="models.Creneau" %>
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
<title>Panier Validé</title>

  <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/superMarket.css">
    <link rel="stylesheet" type="text/css" href="css/panier.css">
</head>
<script src="js/promotion.js"></script>
<body>
<%@ include file="../template/head.jsp" %>
<div>
<%
    String magasinRetrait = (String) session.getAttribute("magasinRetrait");
	String creneauRetrait = (String) session.getAttribute("creneauRetrait");
	Double apayer = (Double) session.getAttribute("apayer");
	Utilisateur user = (Utilisateur) session.getAttribute("user");
	List<Creneau> cx = (List<Creneau>) session.getAttribute("creneaux");
%>
		<h2><%= user.getPrenom()%> <%= user.getNom()%></h2>
        <p>Votre panier a été enregistré, vous pourrez venir le récupérer au créneau choisi.</p>

<%@ include file="../template/footer.jsp" %>
</body>
</html>
<!-- jQuery and Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>


