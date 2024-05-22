<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! @SuppressWarnings("unchecked") %>
<%@page import="java.util.Objects"%>
<%@page import="models.Utilisateur" %>
<%@page import="models.Creneau" %>
<%@page import="java.util.List" %>
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
jsFiles.add("js/promotion.js");

request.setAttribute(AbstractServlet.JS_LIBS_KEY, jsFiles);

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
cssFiles.add("css/validerPanier.css");
cssFiles.add("css/panier.css");

request.setAttribute(AbstractServlet.CSS_LIBS_KEY, cssFiles);

%>
<%@ include file="../template/start.jsp" %>
<div>
	<h1>Choisir un créneau</h1>
<%
String magasinRetrait = (String) session.getAttribute("magasinRetrait");
String creneauRetrait = (String) session.getAttribute("creneauRetrait");
Double apayer = (Double) session.getAttribute("apayer");
Utilisateur user = (Utilisateur) session.getAttribute("user");
List<Creneau> cx = (List<Creneau>) session.getAttribute("creneaux");

if (creneauRetrait == null) {
%>
        <p>Vous n'avez pas encore choisi de créneau.</p>
<%
}
%>

	<form action="PayerServlet" method="post">
	        <% 
	            List<Creneau> tousCreneaux = (List<Creneau>) session.getAttribute("creneaux");
	            for (Creneau creneau: tousCreneaux) { 
	        %>             	
	            <input type="hidden" name="creneau" value="<%= creneau.getCodeCreneau() %>">
		    	<input type="submit" id="bt-creneauSelector" value="<%= creneau.getHeureCreneau().toString().replace("_","-").substring(1) %> le <%= creneau.getDateCreneau() %>">
	        <% 
	            } 
	        %>
	     
	</form>
<% String nombreArrondi = String.format("%.2f", apayer);%>
	<p>A Payer : <span id="apayer" ><%= nombreArrondi %></span> Euro</p>

<%@ include file="../template/end.jsp" %>
