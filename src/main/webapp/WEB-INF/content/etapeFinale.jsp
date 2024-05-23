<%@page import="repositories.StockerRepository"%>
<%@page import="repositories.MagasinRepository"%>
<%@page import="repositories.ArticleRepository"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="models.Article" %>
<%@ page import="models.Magasin" %>
<%
List<String> cssFiles = (List<String>) request.getAttribute(AbstractServlet.CSS_FILES_KEY);
cssFiles = cssFiles == null ? new ArrayList<>() : cssFiles;
cssFiles.add("css/etapeFinale.css");


request.setAttribute(AbstractServlet.CSS_LIBS_KEY, cssFiles);
request.setAttribute("pageTitle", "Articles A Valider");
request.setAttribute("isHeaderDisabled", false);

List<Article> articlesManquants = (List<Article>) session.getAttribute("articlesManquants");
ArticleRepository ar = new ArticleRepository();
MagasinRepository mr = new MagasinRepository();
String magasinId = (String) session.getAttribute("magasinRetrait");
Magasin magasin = (Magasin) mr.findById(Integer.parseInt(magasinId));
StockerRepository sr = new StockerRepository();
List<Integer> produitsId = new ArrayList();
session.setAttribute("articleRempla", produitsId);
%>
<%@ include file="../template/start.jsp" %>

<h1>Vérification des Articles Manquants</h1>
<% if (articlesManquants != null && !articlesManquants.isEmpty()) { %>
    <form action="PayerServlet" method="post">
        Produits Manquants:
                <% for (Article articleManquant : articlesManquants) {
                	
                	List<Article> pourRemplacer = ar.memeSousCategorie(articleManquant.getId(), articleManquant.getSousCategorie());%>
                    <p>Le produit <%= articleManquant.getLib() %> est manquant</p>
                     <p>Liste de remplacement</p>
                     
                         		<% for (Article article : pourRemplacer) { 
                         			
                                	int stock = (int) sr.getQuantiteByArticleAndMagasin(article.getId(), magasin.getCodeMagasin()); 
                               		if(stock>0){
                               		if (!produitsId.contains(article.getId())){
                               			produitsId.add(article.getId());
                               		}
                               		%>
                               		<label><%= article.getLib() %></label>
                                    <input type="number"  min="0" max="<%= stock %>" value="<%= 0 %>" name="<%=article.getId()%>">
                                <% }} %>
                <% } %>

        <button type="submit" class="btn">Valider les Remplacements</button>
    </form>
<% } else { %>
    <p>Aucun article manquant.</p>
    <a  href="PayerServlet">Valider</a>
<% } %>

<%@ include file="../template/end.jsp" %>
