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
	            <%Float prixArticle = article.getPrixUnitaire();
				Float prixReduit=0.0f;
				Float promo = 0.0f;
				Float promotionArticle = article.getPromotion();
	         
	           
	            if (promotionArticle != null) {
	                promo = promotionArticle;
	            }
	            if (promo>0){
	            	prixReduit = prixArticle - (prixArticle * promo / 100);
	            } %>
	                <a href="<%="Article?idArticle="+article.getId() %>"> 
	                <div class="article">
	                <% if (article != null && Boolean.TRUE.equals(article.getBio())) { %>
						 <img class="img-bio-catalogue" src="images/bio.png">
					    <% } %>
	                	<img class ="imgArticle" src="<%= article.getCheminImage() %>">
	                	<div class="articleDetails">
	                	<span class ="nomArticle"><%= article.getLib() %></span><br/>
	                	<div class="price-container">
							<% if (promo >0) { %>
								<p class="price promotion">
									<%= prixArticle %>€
								</p>
								
								<p class="price discount">
									<%=String.format("%.2f",prixReduit)%>€
								</p>
							<%}else{ %>
								<p class="price">
									<%= prixArticle %>€
								</p>
							<%} %>
						</div>
	    				<span class ="poidsArticle"><%= article.getPoids()%>g</span><br/>
                            	<span id="article<%= article.getId() %>">
                            	<% Integer nbr = (Integer) session.getAttribute(article.getId().toString());
									 if (nbr != null ){%>
									<%= nbr %>
									 <%} else {%>
									 0
										 <% }%>
								</span>
						<div id="gestionPanier">
							<i id="enleverButton"  class="boutonPanier fas fa-minus icon"  onclick="enleverAuPanier('<%= article.getId() %>')" title="moins"></i>			
							<i id="ajouterButton"  class="boutonPanier fas fa-plus icon"  title="plus" onclick="ajouterAuPanier('<%= article.getId() %>')"></i>
						</div>
	    				</div>
	                </div> 
	                <% }
		}
	%>
	</div>
</main>
</body>
<%@ include file="../template/footer.jsp" %>
