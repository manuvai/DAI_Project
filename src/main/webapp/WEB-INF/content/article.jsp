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
%>
	<img class ="imgArticle" src="<%= article.getCheminImage() %>">
	<div class="articleDetails">
		<h1 class ="nomArticle"><%= article.getLib() %></h1><br/>
		<span class ="prixArticle"><%= article.getPrixUnitaire() %>€</span><br/>
		<span class ="descArticle">
			<%= article.getDesc() %>
		</span><br/>
		<span class ="poidsArticle"><%= article.getPoids()%>g</span><br/>
		<span class ="prixKilo">
			<%= /*Arrondi à deux décimales*/ String.format("%.2f", (article.getPrixUnitaire() / (float) article.getPoids()) * 1000) %>€ /kg
		</span><br/>
		<img class="img-nutriscore" src="<%= "images/nutriscores/" + article.getNutriscore() + ".png" %>">
		<div id="gestionPanier">
			<i id="enleverButton" class="boutonPanier fas fa-arrow-alt-circle-left ison" onclick="enleverAuPanier('<%= article.getId() %>')" title="moins"></i>
                  	<span id="article<%= article.getId() %>">
                      	<% Integer nbr = (Integer) session.getAttribute(article.getId().toString());
					 if (nbr != null ){%>
					<%= nbr %>
					 <%} else {%>
					 0
						 <% }%>
				</span>
            <i id="ajouterButton" class="boutonPanier fas fa-arrow-alt-circle-right icon" title="plus" onclick="ajouterAuPanier('<%= article.getId() %>')"></i>
		</div>
	</div>
<% 
}
%>
	</div>
<%@ include file="../template/end.jsp" %>
