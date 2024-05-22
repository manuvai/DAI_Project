<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Objects"%>
<%! @SuppressWarnings("unchecked") %>

<%@ page import="models.Rayon" %>
<%@ page import="models.Article" %>
<%@ page import="models.Utilisateur" %>
<%@ page import="models.Magasin" %>
<%@ page import="models.Creneau" %>
<%@ page import="java.util.List" %>
<%@ page import="repositories.ArticleRepository" %>

<%

request.setAttribute("pageTitle", "Panier Validé");

List<String> cssFiles = new ArrayList<>();
cssFiles.add("css/validerPanier.css");
cssFiles.add("css/panier.css");
request.setAttribute(AbstractServlet.CSS_FILES_KEY, cssFiles);

List<String> jsFiles = new ArrayList<>();
jsFiles.add("js/promotion.js");
request.setAttribute(AbstractServlet.JS_FILES_KEY, jsFiles);
%>

<%@ include file="../template/start.jsp" %>
<%
    String magasinRetrait = (String) session.getAttribute("magasinRetrait");
	String creneauRetrait = (String) session.getAttribute("creneauRetrait");
	Double apayer = (Double) session.getAttribute("apayer");
	Utilisateur user = (Utilisateur) session.getAttribute("user");
	List<Creneau> cx = (List<Creneau>) session.getAttribute("creneaux");
%>
<h2><%= user.getPrenom()%> <%= user.getNom()%></h2>
<p>Votre panier a été enregistré, vous pourrez venir le récupérer au créneau choisi.</p>
 <a href="<%= request.getContextPath() %>/home">
        Retourner à l'accueil
    </a>

<%@ include file="../template/end.jsp" %>
