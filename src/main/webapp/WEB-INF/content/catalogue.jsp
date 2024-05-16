<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/catalogue.css">
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
	<div id="sidebar">
		<div id="contentSidebar">
			<select name="categories" id="categories">
			    <% 
			        List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
			        if (categories != null) {
			            for (Categorie categorie : categories) {
			    %><option value="<%=categorie.getNomCategorie() %>"><%=categorie.getNomCategorie() %></option>
			    <% 
			            }
			        } 
			    %>  
		    </select>
	    </div>
	</div>
	<div id="catalogue">
	<% if (request.getAttribute("articles") != null) {%>
	            <% for (Article article : (List<Article>)request.getAttribute("articles")) {%>
	                <a href=""> <div class="article" style="background-image:url('<%= article.getCheminImage() %>');">
	                	<span class ="nomArticle"><%= article.getLib() %></span><br/>
	        			<span class ="prixArticle"><%= article.getPrixUnitaire() %>€</span><br/>
	    				<span class ="poidsArticle"><%= article.getPoids()%>g</span><br/>
	                </div> </a>
	            <% }
		}
	%>
	</div>
</main>
<%@ include file="../template/footer.jsp" %>
