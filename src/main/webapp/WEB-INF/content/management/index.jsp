<%@page import="java.util.List"%>
<%@page import="models.Magasin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% request.setAttribute("pageTitle", "Gestion des articles"); %>

<%@ include file="../../template/head.jsp" %>

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

<%@ include file="../../template/tail.jsp" %>
