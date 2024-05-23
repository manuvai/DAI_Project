<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="models.*" %>
<%@ page import="java.util.List" %>

<%
// =============================
// GESTION DES FICHIERS CSS
// =============================
List<String> cssFiles = (List<String>) request.getAttribute(AbstractServlet.CSS_FILES_KEY);

cssFiles = cssFiles == null ? new ArrayList<>() : cssFiles;
List<String> jsFiles = new ArrayList<>();
/*
	Cette partie traite l'ajout des CDN des librairies JS
	Pour ajouter une nouvelle librairie :
		cssFiles.add("css/example.css");
*/
cssFiles.add("css/article.css");
jsFiles.add("js/home.js");
request.setAttribute(AbstractServlet.CSS_LIBS_KEY, cssFiles);
request.setAttribute(AbstractServlet.JS_FILES_KEY, jsFiles);

%>
<%@ include file="../template/start.jsp" %>
	<div id="article">
<% 
if (request.getAttribute("article") != null) {
Article article = (Article)request.getAttribute("article");

List<ListeDeCourse> listes = (List<ListeDeCourse>) request.getAttribute("listesDeCourse");

Float prixArticle = article.getPrixUnitaire();
Float prixReduit=0.0f;
Float promo = 0.0f;
Float promotionArticle = article.getPromotion(); 
		   
 if (promotionArticle != null) {
	 promo = promotionArticle;
 }
 if (promo>0){
	 prixReduit = prixArticle - (prixArticle * promo / 100);
 } %>

<% if (article != null && Boolean.TRUE.equals(article.getBio())) { %>
	<img class="img-article-detail" src="images/bio.png">
<% } %>
	<img class ="imgArticle" src="<%= article.getCheminImage() %>">
	<div class="articleDetails">
		<h1 class ="nomArticle"><%= article.getLib() %></h1><br/>
		
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
<span class ="descArticle">
			<%= article.getDesc() %>
		</span><br/>
		<span class ="poidsArticle"><%= article.getPoids()%>g</span><br/>
		<span class ="prixKilo">
			<%= /*Arrondi à deux décimales*/ String.format("%.2f", (article.getPrixUnitaire() / (float) article.getPoids()) * 1000) %>€ /kg
		</span><br/>
		<img class="img-nutriscore" src="<%= "images/nutriscores/" + article.getNutriscore() + ".png" %>">
		<div id="gestionPanier">
			<i id="enleverButton" class="boutonPanier fas fa-minus icon"  onclick="enleverAuPanier('<%= article.getId() %>')" title="moins"></i>
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
		
		<% if (listes != null && !listes.isEmpty()) { %>
			<div class="row mt-4">
				<button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
					Ajouter à une liste de courses
				</button>
				<div class="collapse" id="collapseExample">
					<form action="?idArticle=<%= article.getId() %>&action=editListeDeCourse" method="post">
						<input type="hidden" name="article-id" value="<%= article.getId() %>">
						<div class="card card-body">
							<div class="form-group">
							  <label for="">Liste de course</label>
							  <select class="form-control" name="listeDeCourse-id" id="listeDeCourse-id">
							  	<option value="">Choisissez votre liste</option>
							  	<%
							  	for (ListeDeCourse liste : listes) {
							  	%>
								<option value="<%= liste.getIdListDeCourse() %>"><%= liste.getNom() %></option>
								<% } %>
							  </select>
							</div>
							<div class="form-group">
							  <label for="qty">Quantité</label>
							  <input type="text"
							  		class="form-control" 
							  		name="qty" 
							  		id="qty" 
							  		min="0"
									value="1"
							  		aria-describedby="helpId" 
							  		placeholder="">
							</div>
							<button type="submit" class="btn btn-outline-primary">Ajouter</button>
						</div>
					</form>
				</div>
			</div>
		<% } %>
	</div>
<% 
}
%>
	</div>
<%@ include file="../template/end.jsp" %>
