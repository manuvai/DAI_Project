<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="models.*" %>
    <%@ page import="java.util.List" %>
    <!DOCTYPE html>
<title>Online Shop</title>
<script src="js/home.js"></script>
<%@ include file="../template/start.jsp" %>
<link rel="stylesheet" type="text/css" href="css/catalogue.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
 <div id="rayonCarousel" class="carousel slide" data-ride="carousel">
    <div class="carousel-inner">
          <div class="flexANTIBOOTSTRAP">

        <% 
        List<Rayon> rayons = (List<Rayon>) request.getAttribute("rayons");
        if (rayons != null) {
            for (int i = 0; i < rayons.size(); i ++) {
        %>
                <% 
                    Rayon rayon = rayons.get(i);
                %>
                <div class="flex-item">
                    <button onclick="window.location.href='Catalogue?nomRayon=<%=rayon.getNomRayon() %>';" 
                            class="btn-rayon" id="<%= rayon.getId() %>">
                           <img class="img-rayon"
											src="<%= request.getContextPath() %>/<%= rayon.getCheminImageRayon() %>" 
											alt="rayon">
                       <p><%= rayon.getNomRayon() %></p>
                    </button>
                </div>
                <% 
                }
                %>
            </div>
        <% 
            }
        %> 
        </div>

	</div>
</div>
<main>
    <button id="sidebarToggle">Catégories</button>
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
