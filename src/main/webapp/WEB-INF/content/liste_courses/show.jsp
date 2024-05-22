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

<h1>Liste : <%= liste.getNom() %></h1>

<div class="row">

	<table class="table mt-4 col-12">
		<thead>
			<tr>
				<th scope="col">#</th>
				<th scope="col">Nom</th>
				<th scope="col">Quantité</th>
				<th scope="col">Actions</th>
			</tr>
		</thead>
		<tbody>
			<form method="POST" 
				action="<%= request.getContextPath() %>/listes_courses/show?id=<%=liste.getId()%>&action=add">
				<tr>
					<td>
					</td>
					<td>
						<input type="text" 
							name="postItName" 
							id="postItName" 
							class="form-control"
							aria-describedby="postItNameHelp" 
							placeholder="Entrez le nom de la nouvelle postIt" />
						<small id="postItNameHelp" 
							class="form-text text-muted">
							Exemple : Beurre
						</small>
					</td>
					<td>
						<input type="number" 
							name="qty" 
							id="qty" 
							min="0"
							class="form-control"
							aria-describedby="qtyHelp" 
							placeholder="Entrez la quantité de la nouvelle postIt" />
						<small id="qtyHelp" 
							class="form-text text-muted">
							Exemple : 5
						</small>
					</td>
					<td>
						<button type="submit" class="btn btn-success">Ajouter</button>
					</td>
				</tr>
			</form>
			<%
			for (Entry<Article, Integer> entry : articlesMap.entrySet()) {
				Article article = entry.getKey();
				Integer qty = entry.getValue();
				boolean isAdded = addedArticleId != null && addedArticleId.equals(qty);
			%>

			<tr <%= isAdded ? "class=\"table-success\"" : "" %>>
				<th scope="row">
					<%= article.getId() %>
					<i class="fa-solid fa-barcode"></i>
				</th>
				<td>
					<img class="img-thumbnail"
						alt="Image <%= article.getLib() %>" 
						src="<%= request.getContextPath() %>/<%= article.getCheminImage() %>">
					<%= article.getLib() %>
				</td>
				<td><%= qty %></td>
				<td>
					<form action="<%= request.getContextPath() %>/listes_courses/show?action=delete&id=<%= liste.getId() %>"
						method="post">
						<input type="hidden" name="post-it-id"
							value="<%= article.getId() %>" />
						<button type="submit" class="btn btn-danger">Supprimer</button>
					</form>
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
				<th scope="row">
					<%= postIt.getIdPostIt() %>
					<i class="fa-regular fa-note-sticky"></i>
				</th>
				<td class="js-handleArticlesProposal" role="button">
					<input type="hidden" name="postIt-id" value="<%= postIt.getIdPostIt() %>" />
					<input type="hidden" name="postIt-label" value="<%= postIt.getLabel() %>" />
					<%= postIt.getLabel() %>
					<span class="link-secondary">Remplacer par un produit</span>
					<i class="fa-solid fa-hand-pointer" data-id="<%= postIt.getIdPostIt() %>"></i>
				</td>
				<td><%= qty %></td>
				<td>
					<form action="<%= request.getContextPath() %>/listes_courses/show?action=delete&id=<%= liste.getId() %>"
						method="post">
						<input type="hidden" name="post-it-id"
							value="<%= postIt.getIdPostIt() %>" />
						<button type="submit" class="btn btn-danger">Supprimer</button>
					</form>
				</td>
			</tr>

			<%
			}
			%>
		</tbody>
	</table>
	<div class="modal fade" id="js-modalArticlesProposal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">
						Remplacer la post-it par un article
					</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body card-deck"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Annuler</button>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="../../template/end.jsp"%>
