<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="models.Article"%>
<%@page import="java.util.List"%>
<%
boolean isListEmpty = request.getAttribute("articlesCaroussel") == null 
	|| ((List<Article>) request.getAttribute("articlesCaroussel")).isEmpty();

%>
<%@ include file="../template/start.jsp" %>
<h2>Articles correspondant à &quot;<%= request.getParameter("query") %>&quot;</h2>

<%
if (isListEmpty) {
%>
Aucun article trouvé.
<%
} else {
%>

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
	<div class="col-md-3">
		<div class="card">
			<div class="card-body">
			 <% if (article != null && Boolean.TRUE.equals(article.getBio())) { %>
		        <img class="img-bio" src="<%= request.getContextPath() %>/images/bio.png">
		    <% } %>
				<img class="img-item"
					src="<%= request.getContextPath() %>/<%= article.getCheminImage() %>" 
					alt="Image <%= article.getLib() %>/">
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
<%
}
%>
<%@ include file="../template/end.jsp" %>
    
