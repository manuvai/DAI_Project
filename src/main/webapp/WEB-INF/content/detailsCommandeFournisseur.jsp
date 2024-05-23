<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="models.Article" %>
<%@ page import="models.Approvisionner" %>
<%@ page import="models.Commande" %>
<%
request.setAttribute("pageTitle", "Voir Details Commande");
request.setAttribute("isHeaderDisabled", true);
List<String> cssFiles = new ArrayList<>();
cssFiles.add("css/detailsCommandeFournisseur.css");
request.setAttribute(AbstractServlet.CSS_FILES_KEY, cssFiles);
%>
<%@ include file="../template/start.jsp" %>
<h1>Détails de la Commande</h1>

<% 
    Commande commande = (Commande) request.getAttribute("commande");
    Map<Article, Approvisionner> articles = (Map<Article, Approvisionner>) request.getAttribute("articles");
%>

<table class="articles-table">
    <thead>
        <tr>
            <th>Article</th>
            <th>Quantité</th>
        </tr>
    </thead>
    <tbody>
        <% 
            if (articles != null) {
                for (Map.Entry<Article, Approvisionner> entry : articles.entrySet()) {
                    Article article = entry.getKey();
                    Approvisionner approvisionner = entry.getValue();
                    int quantite = approvisionner.getQte();
        %>
        <tr>
            <td><%= article.getLib() %></td>
            <td><%= quantite %></td>
        </tr>
        <% 
                }
            }
        %>
    </tbody>
</table>

<a href="voirCommande" class="btn-back">Retour</a>

<%@ include file="../template/end.jsp" %>
