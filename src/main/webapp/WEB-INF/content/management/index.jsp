<%@page import="java.util.List"%>
<%@page import="models.Magasin"%>
<%@page import="models.Utilisateur.Role"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% request.setAttribute("pageTitle", "Gestion des articles"); %>


<%@ include file="../../template/head.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/footer.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
 <% 
 	Role roleManagement = (Role) session.getAttribute("role");
 if (roleManagement ==null){%>
	 <body>
	    <div class="container">
	             <div class="error-message">
	                Vous n'êtes pas connecté. 
	         
	             </div>    
	             <a href='<%= request.getContextPath() %>/connexion'>S'identifier</a>
	    </div>
		</body>
 <% }else if (roleManagement.equals(Role.GESTIONNAIRE)) { %>
<body>

<h1>Gestion des articles</h1>

<%@ include file="../../template/errors.jsp" %>

<form action="" method="POST" enctype="multipart/form-data">
	    
    <label for="images">Ajouter images produit</label>
    <input type="file" name="images" id="images" accept="image/png, image/jpeg" multiple/>
    <label for="csv">Ajouter CSV</label>
    <input type="file" name="csv" id="csv" accept=".csv" />
    <button type="submit">Ajouter par fichier CSV</button>

    <a href="<%= request.getContextPath() %>/management/stock" id="add-stock-link">Ajouter stock manuellement</a>
</form>
</body>
<% }else{%>
	<body>
    <div class="container">
             <div class="error-message">
                Vous n'avez pas le bon rôle. Veuillez vous connecter.
         
             </div>    
             <a href='<%= request.getContextPath() %>/connexion'>S'identifier</a>
    </div>
</body>
	<%} %>
<%@ include file="../../template/footer.jsp" %>
