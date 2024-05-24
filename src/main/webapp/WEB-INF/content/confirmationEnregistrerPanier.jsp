<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "HomePage"); %>
<%! @SuppressWarnings("unchecked") %>


<%
// =============================
// GESTION DES FICHIERS JS
// =============================
List<String> jsFiles = (List<String>) request.getAttribute(AbstractServlet.JS_FILES_KEY);

jsFiles = jsFiles == null ? new ArrayList<>() : jsFiles;
jsFiles.add("js/mesCommandes.js");

request.setAttribute(AbstractServlet.JS_LIBS_KEY, jsFiles);
%>
<%@ include file="../template/start.jsp" %>


<h2>Confirmation enregistrement</h2>
<p>Votre panier a bien été enregistré. Vous pouvez reprendre votre panier en cours en allant vers la liste de <a  href="<%= request.getContextPath() %>/CommandesClientServlet">vos commandes</a> . </p>

 

<%@ include file="../template/end.jsp" %>
