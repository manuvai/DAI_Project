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
/*
	Cette partie traite l'ajout des CDN des librairies JS
	Pour ajouter une nouvelle librairie :
		jsFiles.add("js/example.js");
*/

request.setAttribute(AbstractServlet.JS_LIBS_KEY, jsFiles);
%>
<%@ include file="../template/start.jsp" %>
<h2>Mes commandes</h2>

<div id="commandes">
 
</div>


<%@ include file="../template/end.jsp" %>
