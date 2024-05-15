<%@page import="java.util.List"%>
<%@page import="models.Magasin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% request.setAttribute("pageTitle", "Gestion des articles"); %>

<%@ include file="../../template/head.jsp" %>

<h1>Gestion des articles</h1>

<form action="" method="POST" enctype="multipart/form-data">
    
    <select name="magasin" id="magasin">
    
        <option value="" selected>Choisir le magasin</option>
        <% if (request.getAttribute("magasin") == null) {%>
	        <% for (Magasin magasin : (List<Magasin>) request.getAttribute("magasins")) {%>
	        	<option value="<%= magasin.getCodeMagasin() %>"><%= magasin.getNomMagasin() %></option>
	        
	        <% } %>
        <% } %>
    </select>
    
    <label for="csv">Ajouter produit</label>
    <input type="file" name="csv" id="csv" accept=".csv" />
    <button type="submit">Ajouter par fichier CSV</button>

    <span id="add-stock-link">Ajouter stock manuellement</span>
</form>

<script>
let magasin = document.getElementById("magasin")
let buttonAdd = document.getElementById("add-stock-link");

buttonAdd.addEventListener("click", () => {
	if (!magasin.value) {
		alert("Vous devez choisir un magasin avant d'aller plus loin");
		return;
	}
    window.location.href = "/management/manual-add?magasinId=" + magasin.value;
})

</script>
<%@ include file="../../template/tail.jsp" %>
