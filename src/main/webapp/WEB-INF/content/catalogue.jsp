<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/catalogue.css">
    <%@ page import="models.*" %>
    <%@ page import="java.util.List" %>
    <!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
			  <option value="" selected disabled hidden>Catégorie</option>
			    <% 
			        List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
			        if (categories != null) {
			            for (Categorie categorie : categories) {
						    %><option class="categorie" value="<%=categorie.getNomCategorie() %>"><%=categorie.getNomCategorie() %></option>
						    <% 
			            }
			        } 
			    %>  
		    </select>
    			<select name="sousCategories" id="sousCategories">
					<option value="" selected disabled hidden>Sous-Catégorie</option>
		    	</select>
	    </div>
	</div>
	<div id="catalogue">
		<% if (request.getAttribute("articles") != null) {%>
            <% for (Article article : (List<Article>)request.getAttribute("articles")) {%>
                <div class="article">
           			<a href="<%="Article?idArticle="+article.getId() %>"> 
		                <% if (article != null && Boolean.TRUE.equals(article.getBio())) { %>
							 <img class="img-bio-catalogue" src="images/bio.png">
					    <% } %>
	                	<img class ="imgArticle" src="<%= article.getCheminImage() %>">
	                	<div class="articleDetails">
		                	<span class ="nomArticle"><%= article.getLib() %></span><br/>
		                	<div class="price-container">
								<% if (article.getPrixUnitaire()!=article.getPrixApresPromotion()) { %>
									<span class="price promotion">
										<%= article.getPrixUnitaire() %>€ 
									</span>
									<span class="price discount">
										<%=String.format("%.2f",article.getPrixApresPromotion())%>€
									</span>
									<%}else{ %>
										<span class="price">
											<%= article.getPrixUnitaire() %>€
										</span>
									<%} %>
							</div>
	    				<span class ="poidsArticle"><%= article.getPoids()%>g</span><br/>
					</a>
						</div>
						<div id="gestionPanier">
							<i id="enleverButton"  class="boutonPanier fas fa-minus icon"  onclick="enleverAuPanier('<%= article.getId() %>')" title="moins"></i>
                            	<span id="article<%= article.getId() %>">
	                            	<% Integer nbr = (Integer) session.getAttribute(article.getId().toString());
										 if (nbr != null ){%>
										<%= nbr %>
										 <%} else {%>
										 0
											 <% }%>
								</span>			
							<i id="ajouterButton"  class="boutonPanier fas fa-plus icon"  title="plus" onclick="ajouterAuPanier('<%= article.getId() %>')"></i>
						</div>
			</div>
           <% }
		}
	%>
	</div>
</main>
</body>
<script src="js/catalogue.js"></script>
<%@ include file="../template/footer.jsp" %>
