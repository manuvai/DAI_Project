<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="models.Article"%>

<%

List<Article> articles = (List<Article>) request.getAttribute("articlesCaroussel");
%>

<div class="container">
	<div class="container">
		<a class="carousel-control-prev" href="#articleCarousel" role="button"
			data-slide="prev"> 
			<span class="carousel-control-prev-icon"
				aria-hidden="true">
			</span>
		</a>
		<div id="articleCarousel" class="carousel slide" data-ride="carousel">
			<div class="carousel-inner">
				<%
				for (int i = 0; i < articles.size(); i += 4) {
				%>
				<div class="carousel-item <%= (i == 0) ? "active" : "" %>">
					<div class="row">
						<%
						for (int j = i; j < Math.min(i + 4, articles.size()); j++) {
							Article article = articles.get(j);
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
							        <img class="img-bio" src="images/bio.png">
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
									
										 
										<i id="enleverButton"
											class="fas fa-arrow-alt-circle-left ison"
											onclick="enleverAuPanier('<%= article.getId() %>')"
											title="moins"></i> 
										<span
											id="article<%= article.getId() %>">
											<%=nbr == null ? 0 : nbr%> 
										</span> 
										<i id="ajouterButton"
											class="fas fa-arrow-alt-circle-right icon" 
											onclick="ajouterAuPanier('<%= article.getId() %>')"
											title="plus"></i>
									</div>
								</div>
							</div>
						</div>
						<%
						}
						%>
					</div>
				</div>
				<%
				}
				%>
			</div>
		</div>
		<a class="carousel-control-next" href="#articleCarousel" role="button"
			data-slide="next"> 
			<span class="carousel-control-next-icon"
				aria-hidden="true">
			</span>
		</a>
	</div>
</div>