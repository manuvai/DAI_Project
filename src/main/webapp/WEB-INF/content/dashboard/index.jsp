<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!@SuppressWarnings("unchecked")%>

<%@page import="java.util.Objects"%>
<%@ page import="models.Article"%>
<%@ page import="java.util.List"%>
<%
List<String> jsFiles = new ArrayList<>();
jsFiles.add("js/home.js");
request.setAttribute(AbstractServlet.JS_FILES_KEY, jsFiles);

List<String> cssFiles = (List<String>) request.getAttribute(AbstractServlet.CSS_FILES_KEY);

cssFiles = cssFiles == null ? new ArrayList<>() : cssFiles;
/*
	Cette partie traite l'ajout des CDN des librairies JS
	Pour ajouter une nouvelle librairie :
		cssFiles.add("css/example.css");
*/
cssFiles.add("css/dashboard.css");

request.setAttribute(AbstractServlet.CSS_LIBS_KEY, cssFiles);

%>

<%@ include file="../../template/start.jsp"%>

<span><h2>Articles commandés le plus fréquemment</h2> <a href="articlesClasses">Voir Tous</a></span>
<% if (request.getAttribute("articlesCaroussel") == null || ((List<Article>) request.getAttribute("articlesCaroussel")).isEmpty()) { %>
	<div class="container">
		Cette liste est vide. <a class="text-mute" href="<%= request.getContextPath() %>/home">Voir quoi ajouter ?</a>
	</div>
<% } else { %>
<%

List<Article> articles = (List<Article>) request.getAttribute("articlesCaroussel");
%>

<div class="row">
	<%
	for (Article article : articles)  {
		Float prixArticle = article.getPrixUnitaire();
		Float prixReduit=0.0f;
		Float promo = 0.0f;
		Float promotionArticle = article.getPromotion();  
	          if (promotionArticle != null) {
	              promo = promotionArticle;
	          }
	          if (promo>0){
	          	prixReduit = prixArticle - (prixArticle * promo / 100);
	          }
		Integer nbr = (Integer) session.getAttribute(article.getId().toString());
	%>
	<div class="col-md-2">
		<div class="card">
			<div class="card-body">
			 <% if (article != null && Boolean.TRUE.equals(article.getBio())) { %>
		        <img class="img-bio" src="<%= request.getContextPath() %>/images/bio.png">
		    <% } %>
		   	<a href="<%="Article?idArticle="+article.getId() %>">
				<img class="img-item"
					src="<%= request.getContextPath() %>/<%= article.getCheminImage() %>" 
					alt="Image <%= article.getLib() %>/">
			</a>
				<p class="card-text"><%= article.getDesc() %></p>
				<img class="img-nutriscore"
					src="<%= request.getContextPath() %>/images/nutriscores/<%= article.getNutriscore() %>.png"
					alt="">
				<div>
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
					<div id="gestionPanier">
						<i id="enleverButton"
							class="boutonPanier fas fa-minus icon"
							onclick="enleverAuPanier('<%= article.getId() %>')"
							title="moins"></i> 
						<span
							id="article<%= article.getId() %>">
							<%=nbr == null ? 0 : nbr%> 
						</span> 
						<i id="ajouterButton"
							class="boutonPanier fas fa-plus icon" 
							onclick="ajouterAuPanier('<%= article.getId() %>')"
							title="plus"></i>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%
	}
	%>
	</div>
<% } %>
<br>
<div>
	<h2>Habitudes de consommation</h2>
	
	<div class="graphDash">
	<div class="bio">
	<p>		Répartion des articles selon s'ils sont bios</p>
		<canvas  id="bioChart"></canvas>
	</div>
	<div class="nutriscore">
	<p>		Répartion des articles selon leur nutriscore</p>
		<canvas  id="nutriChart"></canvas>
	</div>
	<div class="cats">
	<p>		Répartion des articles selon leurs catégories</p>
	<canvas  id="catsChart"></canvas>
	</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	
<script>
  const ctx = document.getElementById('bioChart');
  const bioArt = <%= session.getAttribute("bioArticle") %>
  const nonBioArt = <%= session.getAttribute("nonBioArticle") %>
  new Chart(ctx, {
	type: 'doughnut',
    data: {
      labels: ['Bio', 'Non Bio'],
      datasets: [{
        data: [bioArt,nonBioArt],
        backgroundColor: [
            'rgb(255, 99, 132)',
            'rgb(54, 162, 235)',
          ],
          hoverOffset: 4
      }]
    },
    
  });
  
  const ctxNutri = document.getElementById('nutriChart');
  const a = <%= session.getAttribute("A") %>
  const b = <%= session.getAttribute("B") %>
  const c = <%= session.getAttribute("C") %>
  const d = <%= session.getAttribute("D") %>
  const e = <%= session.getAttribute("E") %>
  new Chart(ctxNutri, {
	type: 'doughnut',
    data: {
      labels: ['A', 'B', 'C', 'D', 'E'],
      datasets: [{
        data: [a,b,c,d,e],
          hoverOffset: 4
      }]
    },
    
  });
  
  const ctxCate = document.getElementById('catsChart');
  new Chart(ctxCate, {
	type: 'doughnut',
    data: {
      labels: <%= session.getAttribute("nomCats") %>,
      datasets: [{
        data: <%= session.getAttribute("numCats") %>,
          hoverOffset: 4
      }]
    },
    
  });
</script>
</div>

<%@ include file="../../template/end.jsp"%>
