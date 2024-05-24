<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="servlets.AbstractServlet"%>
<%@page import="models.Utilisateur.Role" %>

<%

Integer nbrArticleTotal = (Integer) session.getAttribute("nbrArticleTotal");
int nbrArticleString = nbrArticleTotal == null ? 0 : nbrArticleTotal;

Role role = (Role) session.getAttribute("role");

if ((List<String>) request.getAttribute(AbstractServlet.JS_FILES_KEY) == null) {
	request.setAttribute(AbstractServlet.JS_FILES_KEY, new ArrayList<>());
}
((List<String>) request.getAttribute(AbstractServlet.JS_FILES_KEY))
	.add("js/header.js");
%>

<nav class="navbar navbar-expand-lg navbar-light bg-light main-header">
	<input type="hidden" value="<%= request.getContextPath() %>" id="headerRootPath" />
    <a class="navbar-brand" href="<%= request.getContextPath() %>/home">
        <img src="<%= request.getContextPath() %>/images/logo-supermarket.png" class="logo" alt="Supermarch� Logo">
    </a>
    <a class="navbar-brand" href="articlesCroissants">
       <i class=" fas fa-angle-double-up"></i>
    </a>
    <a class="navbar-brand" href="articlesDecroissants">
       <i class=" fas fa-angle-double-down"></i>
    </a>
    <a class="navbar-brand" href="RecettesServlet">
    	<i class="fas fa-cheese"></i>
    </a>
    <div class="navbar-nav ml-auto">
    
    	<% if (role != Role.PREPARATEUR) { %>
	    <form class="form-inline my-2 my-lg-0 dropdown-toggle" 
	    	id="searchForm" 
	    	method="POST"
	    	action="<%= request.getContextPath() %>/search">
	    	<div>
	        <input class="form-control mr-sm-2 dropdown-toggle barre" 
	        	type="search" 
	        	name="query"
	        	placeholder="Rechercher" 
	        	aria-label="Rechercher" 
				data-toggle="dropdown" 
				aria-haspopup="true" 
				aria-expanded="false"
	        	id="searchInput">
	        <div id="searchResults" class="row"></div>
	     </div>
	    </form>
	    <% } %>
	    <% if (role != Role.PREPARATEUR) { %>
        <a class="nav-item nav-link" href="<%= request.getContextPath() %>/panier">
            <i class="fas fa-shopping-cart"></i>
            <span class="badge badge-pill badge-danger" id="nbrPanier"><%= nbrArticleString %></span>
        </a>
        <% } %>
        
       	<% if (role != null && role != Role.PREPARATEUR) { %>
        <a class="nav-item nav-link" href="<%= request.getContextPath() %>/MagasinServlet">
            <i class="fas fa-store"></i>
        </a>
        <% } %>
        <div class="nav-item dropdown">
       	<% if (role == null) { %>
        	<a class="nav-item nav-link" href="<%= request.getContextPath() %>/connexion">
                <i class="fas fa-user"></i>
            </a>
       	<% } else if (role != Role.PREPARATEUR){ %>
            <a class="nav-link dropdown-toggle" href="#" id="profileDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-user"></i>
            </a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="profileDropdown">
                <a class="dropdown-item" href="<%= request.getContextPath() %>/dashboard">Tableau de bord</a>
                <a class="dropdown-item" href="<%= request.getContextPath() %>/CommandesClientServlet">Mes commandes</a>
                <a class="dropdown-item" href="<%= request.getContextPath() %>/listes_courses">Mes listes de course</a>
            </div>
        <% } else {%>
	        <a class="nav-link dropdown-toggle" href="#" id="profileDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                <i class="fas fa-user"></i>
	            </a>
	            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="profileDropdown">
	                <a class="dropdown-item" href="<%= request.getContextPath() %>/PreparationCommandesServlet">Pr�paration des commandes</a>
	                <a class="dropdown-item" href="<%= request.getContextPath() %>/PreparationCommandesHistoriqueServlet">Historique des commandes</a>
	        	</div>
        <% } %>
        </div>
       	<% if (role != null) { %>
        <a class="nav-item nav-link" href="<%= request.getContextPath() %>/DeconnexionServlet">
            <i class="fas fa-sign-out-alt"></i>
        </a>
        <% } %>
    </div>
</nav>

    
