<%@page import="models.Article"%>
<%@page import="java.util.List"%>
<%@page import="models.Magasin"%>
<%@page import="models.Utilisateur.Role"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% 
request.setAttribute("pageTitle", "Gestion des articles" ); 

List<String> jsFiles = new ArrayList<>();
jsFiles.add("js/managementindex.js");
request.setAttribute(AbstractServlet.JS_FILES_KEY, jsFiles);
%>

<%@include file="../../template/start.jsp" %>

<% 
Role roleManagement=(Role) session.getAttribute("role");

if (roleManagement == null) { 
%>
    <div class="error-message">
        Vous n'êtes pas connecté.
    </div>
    <a href='<%= request.getContextPath() %>/connexion'>S'identifier</a>
<% } else if (!roleManagement.equals(Role.GESTIONNAIRE)) { %>
    <div class="error-message">
        Vous n'avez pas le bon rôle. Veuillez vous connecter.
    </div>
    <a href='<%= request.getContextPath() %>/connexion'>S'identifier</a>
<% 
} else {
	List<Article> articles = (List<Article>) request.getAttribute("articles");
%>
	
	<h1>Gestion des articles</h1>
	<div class="container">
		<h2>Ajout de nouveaux articles</h2>
		<div class="row blockquote">
			Pour pouvoir ajouter de nouveaux produits, vous pouvez utiliser 3 types de fichiers :
			<ul>
				<li>Des fichiers images</li>
				<li>Des fichiers CSV</li>
				<li>Des fichiers ZIP</li>
			</ul>
			
			L'ensemble des contenus fournis serviront à établir tous les articles devant être ajoutés.
		</div>
		<form action="" method="POST" enctype="multipart/form-data">
				
			<div class="custom-file col-12 m-4">
				<div class="mb-3">
					<input type="file" 
						name="files" 
						class="custom-file-input" 
						id="files" 
						accept="image/png, image/jpeg, .csv, .zip" 
						required
						multiple>
				    <label class="custom-file-label" for="files">Choisissez les images et fichiers CSV</label>
				</div>
			</div>
			<div class="row m-4">
				
				<div class="col-6">
					<button type="submit" class="btn btn-success">
						Ajouter par fichier CSV
					</button>
				</div>
				<div class="col-6">
					<a href="<%= request.getContextPath() %>/management/stock" 
						class="btn btn-primary"
						id="add-stock-link">
						Afficher le stock prévisionnel par magasin
					</a>
				</div>
			</div>
		
		</form>
	</div>
	<div class="container">
		<h2>Articles contenus dans le catalogue</h2>
		<% if (articles == null || articles.isEmpty()) { %>
			Aucun produit actuellement dans le catalogue.
		<% } else { %>
		<table class="table">
			<thead>
				<tr>
			  		<th scope="col">#</th>
			  		<th scope="col">Image</th>
					<th scope="col">Nom</th>
					<th scope="col">Prix</th>
				</tr>
			</thead>
			<tbody>
				<% for (Article article : articles) { %>
			    <tr>
					<th scope="row" class="align-middle"><%= article.getId() %></th>
					<td class="col-2">
						<img class="img-thumbnail"
							alt="Image <%= article.getLib() %>" 
							src="<%= request.getContextPath() %>/<%= article.getCheminImage() %>">
					</td>
					<td class="align-middle"><%= article.getLib() %></td>
					<td class="align-middle"><%= article.getPrixUnitaire() %> €</td>
			    </tr>
			    <% } %>
			</tbody>
		</table>
		<% } %>
	</div>
<%} %>

<%@ include file="../../template/end.jsp" %>
