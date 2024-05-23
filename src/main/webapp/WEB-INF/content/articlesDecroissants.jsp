
<%@page import="repositories.ArticleRepository"%>
<%@page import="models.Article"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setAttribute("pageTitle", "Articles Tries");

request.setAttribute("isHeaderDisabled", false);
ArticleRepository ar = new ArticleRepository();
List<String> cssFiles = new ArrayList<>();
cssFiles.add("css/articlesTries.css");
request.setAttribute(AbstractServlet.CSS_FILES_KEY, cssFiles);
List<String> jsFiles = new ArrayList<>();
jsFiles.add("js/home.js");
request.setAttribute(AbstractServlet.JS_FILES_KEY, jsFiles);
%>
<%@ include file="../template/start.jsp"%>

<h1>Articles Triés par Ordre Décroissant du prix au kg</h1>

<div>
	<a class="tri" href="articlesCroissants">Croissant</a>
	<%
	List<Article> articles = ar.articlesDecroissants();
	%>

	<table class="articles-table">
		<thead>
			<tr>
				<th>Image de l'article</th>
				<th>Libellé</th>
				<th>Prix au kilo</th>
				<th>Ajouter au panier</th>				
			</tr>
		</thead>
		<tbody>
			<%
			for (Article article : articles) {
			%>
			<tr>
				<td style="text-align: center;">
				    <a href="<%= "Article?idArticle=" + article.getId() %>">
				        <img class="img-item" src="<%= request.getContextPath() + "/" + article.getCheminImage() %>" 
				             alt="Image <%= article.getLib() %>"  style="display: block; margin: 0 auto;"/>
				    </a>
				</td>

				<td> <%=article.getLib()%></td>
				<td><%=String.format("%.2f", article.getPrixUnitaire() * 1000 / article.getPoids())%>
					€/kg</td>
				<td><i id="enleverButton"
					class="boutonPanier fas fa-minus icon"
					onclick="enleverAuPanier('<%=article.getId()%>')" title="moins">
				</i> <%
					 Integer nbr = (Integer) session.getAttribute(article.getId().toString());
					 %> <span id="article<%=article.getId()%>"> <%=nbr != null ? nbr : 0%>
				</span> <i id="ajouterButton" class="boutonPanier fas fa-plus icon"
					title="plus" onclick="ajouterAuPanier('<%=article.getId()%>')">
				</i></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
</div>

<%@ include file="../template/end.jsp"%>
