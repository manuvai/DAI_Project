<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/article.css">
    <%@ page import="models.*" %>
    <%@ page import="java.util.List" %>
    <%@ include file="../template/head.jsp" %>
    <!DOCTYPE html>
<html>
<head>

<link rel="icon" href="images/logo-supermarket.png" type="image/x-icon"> 
<link rel="stylesheet" type="text/css" href="css/header.css">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<title>Online Shop</title>
    
<main>
	<div id="article">
	<% if (request.getAttribute("article") != null) {Article article = (Article)request.getAttribute("article");%>
        	<img class ="imgArticle" src="<%= article.getCheminImage() %>">
	                	<div class="articleDetails">
		                	<h1 class ="nomArticle"><%= article.getLib() %></h2><br/>
            				<span class ="prixArticle"><%= article.getPrixUnitaire() %>€</span><br/>
         			        <span class ="descArticle"><%= article.getDesc() %> description description lalala lorem ipsul doloret sit amet description description lalala lorem ipsul doloret sit amet </span><br/>
		    				<span class ="poidsArticle"><%= article.getPoids()%>g</span><br/>
		    				<span class ="prixKilo"><%= (article.getPrixUnitaire()/(float)article.getPoids())*1000%>€ / kg</span><br/>
		    				<img class="img-nutriscore" src="<%= "images/nutriscores/" + article.getNutriscore() + ".png" %>">
	    				</div>
	                </div>
	            <% }
	%>
	</div>
</main>
<%@ include file="../template/footer.jsp" %>
