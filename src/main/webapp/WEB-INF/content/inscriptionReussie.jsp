<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inscription Reussie</title>
</head>
<body>
    <div class="container">
        <h1>Inscription réussie !</h1>
        <p>Votre inscription s'est bien déroulée, <strong><%= session.getAttribute("prenom") %> <%= session.getAttribute("nom") %></strong>. Bienvenue sur notre site !</p>
        <p><a href="home">Allez sur la page d'accueil</a></p>
    </div>
</body>
</html>