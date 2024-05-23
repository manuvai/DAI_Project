<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "HomePage"); %>
<%! @SuppressWarnings("unchecked") %>

<%@page import="java.util.Objects"%>
<%@ page import="models.Panier" %>
<%@ page import="java.util.List" %>
<%@page import="models.Article"%>
<%
// =============================
// GESTION DES FICHIERS JS
// =============================
List<String> jsFiles = (List<String>) request.getAttribute(AbstractServlet.JS_FILES_KEY);

jsFiles = jsFiles == null ? new ArrayList<>() : jsFiles;
/*
	Cette partie traite l'ajout des CDN des librairies JS
	Pour ajouter une nouvelle librairie :
		jsFiles.add("js/example.js");
*/

request.setAttribute(AbstractServlet.JS_LIBS_KEY, jsFiles);
%>
<%@ include file="../template/start.jsp" %>
<h2>Détails de la commande <%= "N° " + request.getAttribute("idCommande") %></h2>

<div id="articles" class="container">
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Image de l'article</th>
                <th>Nom article</th>
                <th>Quantité</th>
                <th>Prix unitaire</th>
                <th>Promotion </th>
                <th>Prix total</th>
                <th></th>
                
                
            </tr>
        </thead>
        <tbody>
           <%
		    Map<Article, Integer> mapArticles = (Map<Article, Integer>) request.getAttribute("articlesQte");
            double totalPanier = 0.0; 
		    if (mapArticles != null) {
		        for (Map.Entry<Article, Integer> entry : mapArticles.entrySet()) {
		            Article article = entry.getKey();
		            int quantite = entry.getValue();
		            totalPanier += article.getPrixUnitaire() * quantite * (1 - article.getPromotion() / 100);
		%>
		    <tr>
		    	<td>
		    	 <% if (article != null && Boolean.TRUE.equals(article.getBio())) { %>
							        <img class="img-bio-catalogue" src="images/bio.png">
							    <% } %>
				<a href="<%="Article?idArticle="+article.getId() %>">
		    	<img class="img-item"
				src="<%= request.getContextPath() %>/<%= article.getCheminImage() %>" ></a>
				</td>
		        <td><%= article.getDesc() %></td>
		        <td><%= quantite %></td>
		        <td><%= article.getPrixUnitaire() %> €</td>
		        <td style='color:red'>- <%= article.getPromotion() %> %</td>
		        <td><strong>
		        <%=String.format("%.2f", article.getPrixUnitaire()*quantite*(1-article.getPromotion()/100))%> €
		       </strong></td>
		    </tr>
		<%
		        }
		    }
		%>
		
        </tbody>
        <tfoot>
        <tr>
            <td colspan="5" class="text-end"></td>
            <td colspan="2" class="text-end" style="font-size: 1.5em;"><strong>Total:<%= String.format("%.2f", totalPanier) %>€</strong></td>
        </tr>
    </tfoot>
    </table>

   	<div style="display:flex">
   	 <a href="<%= request.getContextPath() %>/CommandesClientServlet"><button  class="btn btn-primary">Retour sur mes commandes</button></a>
     
     <form action="ReprendrePanierServlet" method="post">
     	<input type="hidden" id="idPanierReprendre" name="idPanierReprendre" value=<%=request.getAttribute("idCommande")  %>>
     	<button  type="submit" class="btn btn-secondary">Reprendre mon panier</button>
     </form>
   	</div>
   	
    
     
</div>



<%@ include file="../template/end.jsp" %>
