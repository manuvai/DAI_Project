<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Objects"%>
<%! @SuppressWarnings("unchecked") %>

<%@ page import="models.Rayon" %>
<%@ page import="models.Article" %>
<%@ page import="models.Utilisateur" %>
<%@ page import="models.Magasin" %>
<%@ page import="java.util.List" %>
<%@ page import="repositories.ArticleRepository" %>
<%
request.setAttribute("pageTitle", "Valider Panier");

List<String> cssFiles = new ArrayList<>();
cssFiles.add("css/validerPanier.css");
cssFiles.add("css/panier.css");
request.setAttribute(AbstractServlet.CSS_FILES_KEY, cssFiles);

List<String> jsFiles = new ArrayList<>();
jsFiles.add("js/home.js");
jsFiles.add("js/promotion.js");
request.setAttribute(AbstractServlet.JS_FILES_KEY, jsFiles);

%>
<%@ include file="../template/start.jsp" %>
<div>
<h1>Validation du panier</h1>
<%
    String magasinRetrait = (String) session.getAttribute("magasinRetrait");
	Double totalValidation = (Double) session.getAttribute("totalPanierValidation");
	String nombreArrondi = String.format("%.2f", totalValidation);
	Utilisateur user = (Utilisateur) session.getAttribute("user");

    if (magasinRetrait == null) {
%>
        <p>Vous n'avez pas encore choisi de magasin.</p>
<%
    }
%>

<form action="ChoisirCreneauServlet" method="post">
    <label for="magasin">Choisir un magasin :</label>
    <select name="magasin" id="magasin">
        <% 
            List<Magasin> tousMagasins = (List<Magasin>) session.getAttribute("tousMagasins");
            for (Magasin magasin : tousMagasins) {
            	if(magasinRetrait != null && magasinRetrait.equals(Integer.toString(magasin.getCodeMagasin()))){
        %>	
                <option value="<%= magasin.getCodeMagasin() %>" selected><%= magasin.getNomMagasin() %></option>
        <% 
            	}else {
            		%> 
            		 <option value="<%= magasin.getCodeMagasin() %>" ><%= magasin.getNomMagasin() %></option>
            		<%
            	} 
            	}
        %>
    </select>
    <br>
    <label for="utiliserPoints">Utiliser les points de fidélité : (<span id="ptFidel"><%= user.getPtFidelite()%></span> pts)</label>
    <input type="checkbox" id="utiliserPoints" name="utiliserPoints" value="true">
    <br>
    <input type="submit" value="Choisir le creneau">
</form>
Total : <span id="totalPP" ><%= nombreArrondi%></span>€

<%@ include file="../template/end.jsp" %>


