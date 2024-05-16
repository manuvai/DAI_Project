<%@page import="java.util.Date"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="dtos.ArticleStockDto"%>
<%@page import="java.util.List"%>
<%@page import="models.Magasin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% request.setAttribute("pageTitle", "Gestion des articles"); %>

<!DOCTYPE html>
<html>
<head>

<link rel="icon" href="images/logo-supermarket.png" type="image/x-icon"> 
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<title>Online Shop</title>

  <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/superMarket.css">
</head>
<body>
<%@ include file="../../template/head.jsp" %>
<%@ include file="../../template/errors.jsp" %>
<div class="content">

<h1>Visualisation des stocks</h1>

<select name="magasin-id" id="magasin-id">
  <option value="" <%= request.getParameter("magasin-id") != null ? "" : "selected" %>> choisir un magasin</option>
  <% if (request.getAttribute("magasins") != null) {
        for (Magasin magasin : (List<Magasin>) request.getAttribute("magasins"))  {
        	boolean isSelected = Integer.toString(magasin.getCodeMagasin()).equals(request.getParameter("magasin-id"));
  %>
    <option value="<%= magasin.getCodeMagasin()%>" <%= isSelected ? "selected" : "" %>><%= magasin.getNomMagasin() %></option>
  <%
        }
  }
  %>
</select>

<% if (request.getAttribute("articles") != null && ((List<ArticleStockDto>) request.getAttribute("articles")).size() > 0) {
	List<ArticleStockDto> articles = (List<ArticleStockDto>) request.getAttribute("articles"); %>
	
	<h2>Articles</h2>
  <table class="table">
    <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">Nom</th>
        <th scope="col">Image</th>
        <th scope="col">Stock</th>
      </tr>
    </thead>
    <tbody>
	
	<% for (ArticleStockDto dto : articles) { %>
        <tr>
          	<th scope="row"><%= dto.getId() %></th>
          	<td><%= dto.getLabel() %></td>
          	<td>
          		<img src="<%= request.getContextPath() %>/images/articles/<%= dto.getImagePath() %>" alt="<%= dto.getLabel() %>" class="img-thumbnail">
        	</td>
          	<td>
          		<ul>
          		<% for (Entry<Date, Integer> entry : dto.getStocks().entrySet()) { %>
          		
          		<li><%= entry.getKey() %> : <%= entry.getValue() %></li>
          		
          		<% } %>
          		</ul>
          	</td>
        </tr>
	<% } %>
    </tbody>
  </table>
	
<% } %>
</div>

<script>
document.addEventListener("DOMContentLoaded", () => {

	document.getElementById("magasin-id")
		.addEventListener("change", redirect);

});

function redirect() {
	
	let magasinId = document.getElementById("magasin-id").value;
	
	let nextLink = "<%= request.getContextPath() %>/management/stock?magasin-id=" + magasinId;
	window.location.href = nextLink;
}
</script>
<!-- jQuery and Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>

