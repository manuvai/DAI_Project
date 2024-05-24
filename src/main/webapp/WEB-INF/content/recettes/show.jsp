<%@page import="models.Article"%>
<%@page import="models.PostIt"%>
<%@page import="dtos.ListeCourseDto"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

List<String> jsFiles = (List<String>) request.getAttribute(AbstractServlet.JS_FILES_KEY);
jsFiles = jsFiles == null ? new ArrayList<>() : jsFiles;

jsFiles.add("js/liste_courses_show.js");

request.setAttribute(AbstractServlet.JS_FILES_KEY, jsFiles);

ListeCourseDto liste = (ListeCourseDto) request.getAttribute("liste");
Map<PostIt, Integer> postItsMap = liste.getPostsItsMap();
Map<Article, Integer> articlesMap = liste.getArticlesMap();

Integer addedPostItId = (Integer) request.getAttribute("addedPostItId");
Integer addedArticleId = (Integer) request.getAttribute("addedArticleId");
%>
<%@ include file="../../template/start.jsp"%>

<input type="hidden" name="rootPath" id="rootPath" value="<%= request.getContextPath() %>" />
<input type="hidden" name="listeId" id="listeId" value="<%= liste.getId() %>" />

<h1>Recette : <%= liste.getNom() %></h1>
<a href="<%= request.getContextPath() %>/RecettesServlet">
	Retour aux recettes
</a>

<div class="row">

	<table class="table mt-4 col-12">
		<thead>
			<tr>
				<th scope="col">Nom</th>
				<th scope="col">Quantité</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Entry<Article, Integer> entry : articlesMap.entrySet()) {
				Article article = entry.getKey();
				Integer qty = entry.getValue();
				boolean isAdded = addedArticleId != null && addedArticleId.equals(qty);
			%>

			<tr <%= isAdded ? "class=\"table-success\"" : "" %> id="article-<%= article.getId() %>">
				<th scope="row">
					<%= article.getId() %>
					<i class="fa-solid fa-barcode"></i>
				</th>
				<td>
					<img class="img-thumbnail col-4"
						alt="Image <%= article.getLib() %>" 
						src="<%= request.getContextPath() %>/<%= article.getCheminImage() %>">
					<span class="col-8"><%= article.getLib() %></span>
				</td>
				<td>
					<input type="number" 
						class="form-control js-articleQty" 
						min="0" 
						name="article-qty" 
						value="<%= qty %>" />
				</td>
			</tr>

			<%
			}
			%>
			
			
			<%
			for (Entry<PostIt, Integer> entry : postItsMap.entrySet()) {
				PostIt postIt = entry.getKey();
				Integer qty = entry.getValue();
				boolean isAdded = addedPostItId != null && addedPostItId.equals(qty);
			%>

			<tr <%= isAdded ? "class=\"table-success\"" : "" %>>
				<td>
					<%= postIt.getLabel() %>
				</td>
				<td><%= qty %></td>
			</tr>

			<%
			}
			%>
		</tbody>
	</table>
	<%@ include file="modal_articles_proposal.html" %>
</div>

<%@ include file="../../template/end.jsp"%>
