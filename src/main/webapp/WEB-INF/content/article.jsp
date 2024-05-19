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
/*
	Cette partie traite l'ajout des CDN des librairies JS
	Pour ajouter une nouvelle librairie :
		cssFiles.add("css/example.css");
*/
cssFiles.add("css/article.css");

request.setAttribute(AbstractServlet.CSS_LIBS_KEY, cssFiles);

%>
<%@ include file="../template/start.jsp" %>
<main>
	<div id="article">
<% 
if (request.getAttribute("article") != null) {
	Article article = (Article)request.getAttribute("article");
%>
		<img class ="imgArticle" src="<%= article.getCheminImage() %>">
		<div class="articleDetails">
			<h1 class ="nomArticle"><%= article.getLib() %></h1><br/>
			<span class ="prixArticle"><%= article.getPrixUnitaire() %>€</span><br/>
			<span class ="descArticle">
				<%= article.getDesc() %>
			</span><br/>
			<span class ="poidsArticle"><%= article.getPoids()%>g</span><br/>
			<span class ="prixKilo"><%= (article.getPrixUnitaire() / (float) article.getPoids()) * 1000%>€ / kg</span><br/>
			<img class="img-nutriscore" src="<%= "images/nutriscores/" + article.getNutriscore() + ".png" %>">
		</div>
<% 
}
%>
	</div>
</main>
<%@ include file="../template/end.jsp" %>
