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
        <img src="<%= request.getContextPath() %>/images/logo-supermarket.png" class="logo" alt="Supermarché Logo">
    </a>
    <div class="navbar-nav ml-auto">
    
	    <form class="form-inline my-2 my-lg-0 dropdown-toggle" 
	    	id="searchForm" 
	    	method="POST"
	    	action="<%= request.getContextPath() %>/articles/search">
	        <input class="form-control mr-sm-2 dropdown-toggle" 
	        	type="search" 
	        	name="query"
	        	placeholder="Rechercher" 
	        	aria-label="Rechercher" 
				data-toggle="dropdown" 
				aria-haspopup="true" 
				aria-expanded="false"
	        	id="searchInput">
	        <div id="searchResults" 
	        	class="dropdown-menu dropdown-menu-right" 
	        	aria-labelledby="searchInput"></div>
	    </form>
        <a class="nav-item nav-link" href="<%= request.getContextPath() %>/panier">
            <i class="fas fa-shopping-cart"></i>
            <span class="badge badge-pill badge-danger" id="nbrPanier"><%= nbrArticleString %></span>
        </a>
        
       	<% if (role != null) { %>
        <a class="nav-item nav-link" href="<%= request.getContextPath() %>/MagasinServlet">
            <i class="fas fa-store"></i>
        </a>
        <% } %>
        <div class="nav-item dropdown">
       	<% if (role == null) { %>
        	<a class="nav-item nav-link" href="<%= request.getContextPath() %>/connexion">
                <i class="fas fa-user"></i>
            </a>
       	<% } else { %>
            <a class="nav-link dropdown-toggle" href="#" id="profileDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-user"></i>
            </a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="profileDropdown">
                <a class="dropdown-item" href="<%= request.getContextPath() %>/dashboard">Tableau de bord</a>
                <a class="dropdown-item" href="<%= request.getContextPath() %>/CommandesClientServlet">Commandes</a>
                <a class="dropdown-item" href="<%= request.getContextPath() %>/listes_courses">Mes listes de course</a>
                <a class="dropdown-item" href="#">Paramètres</a>
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
