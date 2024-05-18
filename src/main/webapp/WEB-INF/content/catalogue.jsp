<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/catalogue.css">
    <%@ page import="models.*" %>
    <%@ page import="java.util.List" %>
    <!DOCTYPE html>
<html>
<head>

<link rel="icon" href="images/logo-supermarket.png" type="image/x-icon"> 
<link rel="stylesheet" type="text/css" href="css/header.css">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
   <link rel="stylesheet" type="text/css" href="css/superMarket.css">
<title>Online Shop</title>
</head>
<script src="js/home.js"></script>
  <body>
<%@ include file="../template/head.jsp" %>
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
	    </div>.
	</div>
	<div id="catalogue">
	<% if (request.getAttribute("articles") != null) {%>
	            <% for (Article article : (List<Article>)request.getAttribute("articles")) {%>
	                <a href="<%="Article?idArticle="+article.getId() %>"> 
	                <div class="article">
	                	<img class ="imgArticle" src="<%= article.getCheminImage() %>">
	                	<div class="articleDetails">
	                	<span class ="nomArticle"><%= article.getLib() %></span><br/>
	        			<span class ="prixArticle"><%= article.getPrixUnitaire() %>€</span><br/>
	    				<span class ="poidsArticle"><%= article.getPoids()%>g</span><br/>
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
	            
	    				</div>
	                </div> 
	                <% }
		}
	%>
	</div>
</main>
</body>
<%@ include file="../template/footer.jsp" %>
