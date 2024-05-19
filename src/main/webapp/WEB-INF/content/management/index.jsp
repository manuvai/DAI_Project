<%@page import="java.util.List"%>
<%@page import="models.Magasin"%>
<%@page import="models.Utilisateur.Role"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% request.setAttribute("pageTitle", "Gestion des articles" ); %>

<%@include file="../../template/start.jsp" %>
<% 
Role roleManagement=(Role) session.getAttribute("role");

if (roleManagement == null) { 
%>

    <div class="error-message">
        Vous n'êtes pas connecté.

    </div>
    <a href='<%= request.getContextPath() %>/connexion'>S'identifier</a>
    <% } else if (roleManagement.equals(Role.GESTIONNAIRE)) { %>

	
	<div class="container">

		<h1>Gestion des articles</h1>
		
		<%@ include file="../../template/errors.jsp" %>
		
		<form action="" method="POST" enctype="multipart/form-data">
				
			<div class="custom-file col-md-4">
				<input type="file" name="images" id="images" accept="image/png, image/jpeg" multiple>
				<label class="custom-file-label" for="images">Choisissez les images</label>
				<div class="invalid-feedback">Veuillez vérifier les images soumies</div>
			</div>
			<div class="custom-file col-md-4">
				<input type="file" name="images" id="images" accept="image/png, image/jpeg" multiple/>
				<label class="custom-file-label" for="images">Choisissez le fichier CSV</label>
				<div class="invalid-feedback">Veuillez vérifier le fichier CSV fourni</div>
			</div>
			<div class="col-md-4">
				<button type="submit" class="btn btn-success">Ajouter par fichier CSV</button>
				<a href="<%= request.getContextPath() %>/management/stock" id="add-stock-link">Ajouter stock manuellement</a>
			</div>
		
		</form>
	</div>
        <% }else{%>

        <div class="error-message">
            Vous n'avez pas le bon rôle. Veuillez vous connecter.

        </div>
        <a href='<%= request.getContextPath() %>/connexion'>S'identifier</a>
<%} %>
<%@ include file="../../template/end.jsp" %>
