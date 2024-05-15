<%@page import="repositories.PanierRepository, models.Panier, java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "preparationcommandes"); %>

<%@ include file="../template/head.jsp" %>
<link rel="stylesheet" type="text/css" href="css/preparationcommandes.css">

<h1>Page de préparation des commandes</h1>
 
	<table class="blueTable">
	<thead>
	<tr>
	<th>Commandes en cours</th>
	<th>Date de collecte</th>
	</tr>
	</thead>
	<tfoot>
	<tr>
	<td colspan="2">
	<div class="links"><a href="#">&laquo;</a> <a class="active" href="#">1</a> <a href="#">2</a> <a href="#">3</a> <a href="#">4</a> <a href="#">&raquo;</a></div>
	</td>
	</tr>
	</tfoot>
	<tbody>
	<tr>
	<td><a href="#"> #18945849289</a></td>
	<td>29/04/2024 <br> 10h30</td>
	</tr>
	<tr>
	<td><a href="#"> #18945849289</a></td>
	<td>29/04/2024 <br> 10h30</td>
	</tr>
	<tr>
	<td><a href="#"> #18945849289</a></td>
	<td>29/04/2024 <br> 10h30</td>
	</tr>
	<tr>
	<td><a href="#"> #18945849289</a></td>
	<td>29/04/2024 <br> 10h30</td>
	</tr>
	</tbody>
	</table>
	
<%@ include file="../template/tail.jsp" %>
