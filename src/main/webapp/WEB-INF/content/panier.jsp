<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Objects"%>
<%! @SuppressWarnings("unchecked") %>

<%@ page import="models.Rayon" %>
<%@ page import="models.Article" %>
<%@ page import="models.Utilisateur" %>
<%@ page import="java.util.List" %>
<%@ page import="repositories.ArticleRepository" %>
<!DOCTYPE html>
<html>
<head>

<link rel="icon" href="images/logo-supermarket.png" type="image/x-icon"> 
<link rel="stylesheet" type="text/css" href="css/header.css">
<script src="js/home.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<title>Panier</title>

  <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/superMarket.css">
    <link rel="stylesheet" type="text/css" href="css/panier.css">
</head>
<script src="js/panier.js"></script>
<body>
<%@ include file="../template/head.jsp" %>
<div>
<h1>Bonjour, voici votre panier</h1>

    <% 
        List<String> numeros = (List<String>) session.getAttribute("numeros");
    	ArticleRepository articleR = new ArticleRepository();
    	Utilisateur user = (Utilisateur) session.getAttribute("user");
    %>
    
    <% if (numeros != null && !numeros.isEmpty()) { 
    float total = 0;%>
    <h2>Vous avez actuellement une quantité totale de <%= session.getAttribute("nbrArticleTotal") %> article(s)</h2>

<h3>Liste des Articles Ajoutés</h1>
    
        <ul>
            <% for (String numero : numeros) { %>
            <% if ((int) session.getAttribute(numero)>0) { 
            Article article = articleR.findById(Integer.parseInt(numero));%>
            
                  
            	<div class="row ">
	                	<img class ="imgArticle" src="<%= article.getCheminImage() %>">
	                	<span class ="nomArticle"><%= article.getLib() %></span><br/>
	        			<span  class ="prixArticle"><span id="prixUnitaire<%= article.getId() %>"><%= article.getPrixUnitaire() %></span>€</span><br/>
	    				<i id="enleverButton" class="fas fa-arrow-alt-circle-left ison" onclick="enleverAuPanier('<%= article.getId() %>')" title="moins"></i>
                            	<span id="article<%= article.getId() %>">
                            	<% Integer nbr = (Integer) session.getAttribute(article.getId().toString());
									 if (nbr != null ){%>
									<%= nbr %>
									 <%} else {%>
									 0
										 <% }%>
								</span>
                    
                    <i id="ajouterButton" class="fas fa-arrow-alt-circle-right icon" title="plus" onclick="ajouterAuPanier('<%= article.getId() %>')"></i></a>
					<% if (article.getPromotion()> 0) { %>
					<span class ="promotion">-  <span id="promotion<%= article.getId() %>"><%=article.getPromotion()%></span>%</span><br/>
					   <% }%>
					   	 <%total+=article.getPrixUnitaire()*nbr*(1-article.getPromotion()/100);%>
					<span id="prix<%= article.getId() %>" class ="prixTotal"><%= article.getPrixUnitaire()*nbr*(1-article.getPromotion()/100) %>€</span><br/>
	                </div> 
	                
  
            
            <% }} %>
        </ul>
        <h1>Total: <span id="total"><%= total %>€</span></h1>
        <% if (user != null) { %>
        <h1>Points Fidélités: <%= user.getPtFidelite() %></h1>
        <% if (user.getPtFidelite()>9) { %>
        <h1>Vous pouvez les utiliser et gagner <%= user.getPtFidelite()/10 %>€</h1>
    <% } }%>
         <% if (total != 0) { %>
         <a href="validerPanier" class="validerP">Valider le panier</a>
          <%}%>
  <%   }else{ %>
    <h2>Pas encore d'articles</h2>
      <%}%>
</div>



<%@ include file="../template/footer.jsp" %>
</body>
</html>
<!-- jQuery and Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>



