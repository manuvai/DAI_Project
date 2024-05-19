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
							Integer nbr = (Integer) session.getAttribute(article.getId().toString());
						%>
						<div class="col-md-3">
							<div class="card">
								<div class="card-body">
									<img class="img-item"
										src="<%= request.getContextPath() %>/<%= article.getCheminImage() %>" 
										alt="Image <%= article.getLib() %>/">
									<p class="card-text"><%= article.getDesc() %></p>
									<img class="img-nutriscore"
										src="<%= request.getContextPath() %>/images/nutriscores/<%= article.getNutriscore() %>.png"
										alt="">
									<div>
										<p class="price">
											<%= article.getPrixUnitaire() %>€
										</p>
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