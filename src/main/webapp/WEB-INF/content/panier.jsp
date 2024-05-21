<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! @SuppressWarnings("unchecked") %>

<%@ page import="java.util.Objects"%>
<%@ page import="models.Rayon" %>
<%@ page import="models.Article" %>
<%@ page import="models.Utilisateur" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="repositories.ArticleRepository" %>
<%@ page import="servlets.AbstractServlet" %>

<%
List<String> cssFiles = new ArrayList<>();
cssFiles.add("css/panier.css");
request.setAttribute(AbstractServlet.CSS_FILES_KEY, cssFiles);

List<String> jsFiles = new ArrayList<>();
jsFiles.add("js/panier.js");
request.setAttribute(AbstractServlet.JS_FILES_KEY, jsFiles);
%>
<%@ include file="../template/start.jsp" %>

	<h1>Bonjour, voici votre panier</h1>
	
	<% 
	List<String> numeros = (List<String>) session.getAttribute("numeros");
	ArticleRepository articleR = new ArticleRepository();
	Utilisateur user = (Utilisateur) session.getAttribute("user");
	if (numeros != null && !numeros.isEmpty()) { 
	    float total = 0;
	%>
    <h2>Vous avez actuellement une quantité totale de <%= session.getAttribute("nbrArticleTotal") %> article(s)</h2>

	<h3>Liste des Articles Ajoutés</h3>
    
        <ul>
	<% 
	for (String numero : numeros) { 
		if ((int) session.getAttribute(numero) > 0) { 
	            Article article = articleR.findById(Integer.parseInt(numero));
	%>
            
                  
            	<div class="row">
                	<img class ="imgArticle" src="<%= article.getCheminImage() %>">
                	<span class ="nomArticle"><%= article.getLib() %></span><br/>
        			<span  class ="prixArticle">
        				<span id="prixUnitaire<%= article.getId() %>"><%= article.getPrixUnitaire() %></span>€
       				</span><br/>
    				<i id="enleverButton" 
    					class="fas fa-arrow-alt-circle-left ison" 
    					onclick="enleverAuPanier('<%= article.getId() %>')" 
    					title="moins">
   					</i>
					<% 
					Integer nbr = (Integer) session.getAttribute(article.getId().toString());
					%>
					<span id="article<%= article.getId() %>">
						<%= nbr != null ? nbr : 0 %>
					</span>
                    
                    <i id="ajouterButton" 
                    	class="fas fa-arrow-alt-circle-right icon" 
                    	title="plus" 
                    	onclick="ajouterAuPanier('<%= article.getId() %>')">
                   	</i>
					<%
					if (article.getPromotion()> 0) { 
					%>
					<span class ="promotion">-
						<span id="promotion<%= article.getId() %>">
							<%=article.getPromotion()%>
						</span>%
					</span><br/>
					<% 
					}
					%>
					<span id="prix<%= article.getId() %>" 
						class ="prixTotal">
						<%=  String.format("%.2f", article.getPrixUnitaire()*nbr*(1-article.getPromotion()/100)) %>€
					</span><br/>
                </div> 
	<%
			total += article.getPrixUnitaire() * nbr * (1 - article.getPromotion() / 100);
		}
	} 
	%>
        </ul>
        <% String nombreArrondi = String.format("%.2f", total);%>
        <h1>Total: <span id="total"><%= nombreArrondi %>€</span></h1>
<% 
        if (user != null) { 
%>
        <h1>Points Fidélités: <%= user.getPtFidelite() %></h1>
	        <% if (user.getPtFidelite() > 9) { %>
	        <h1>Vous pouvez les utiliser et gagner jusqu'à <%= user.getPtFidelite()/10 %>€</h1>
	    	<% } 
        }
		if (total != 0) { %>
         <a href="ValiderPanierServlet" class="validerP">Valider le panier</a>
          <%}%>
  <%   } else { %>
    <h2>Pas encore d'articles</h2>
<%
}
%>

<%@ include file="../template/end.jsp" %>

