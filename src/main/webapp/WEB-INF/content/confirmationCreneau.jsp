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


<h2>Confirmation modification creneau</h2>
<p>Votre modification de creneau a bien été prise en compte.</p>

  <a href="<%= request.getContextPath() %>/CommandesClientServlet"><button  class="btn btn-primary">Retour sur mes commandes</button></a>

<%@ include file="../template/end.jsp" %>
