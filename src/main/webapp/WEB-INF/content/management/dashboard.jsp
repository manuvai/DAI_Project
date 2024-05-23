
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% 
request.setAttribute("pageTitle", "Dashboard"); 

Long avgTempsPreparation = request.getAttribute("avgTempsPreparation") == null
	? 0
	: (Long) request.getAttribute("avgTempsPreparation");

long seconds = avgTempsPreparation / 1000;
long minutes = seconds / 60;
long hours = minutes / 60;
long days = hours / 24;

long remainingSeconds = seconds % 60;
long remainingMinutes = minutes % 60;
long remainingHours = hours % 24;
%>

<%@ include file="../../template/start.jsp" %>

<h1>Dashboard</h1>
<a href="<%= request.getContextPath() %>/management/">Retour à la page d'accueil</a>

<h2>Temps de préparation</h2>
<div class="container mb-2">
	<div class="rounded bg-gradient-4 shadow p-5 text-center mb-5">
           <p class="mb-0 font-weight-bold text-uppercase">Temps moyen de préparation des paniers</p>
           <div id="clock-c" class="countdown py-4">
           	<span class="h1 font-weight-bold"><%= days %></span> Jours
          	<span class="h1 font-weight-bold"><%= remainingHours %></span> Heures
          	<span class="h1 font-weight-bold"><%= remainingMinutes %></span> Minutes
          	<span class="h1 font-weight-bold"><%= remainingSeconds %></span> Secondes
          	</div>
       </div>
</div>

<input type="hidden" id="rootPath" value="<%= request.getContextPath() %>">

<%@ include file="../../template/end.jsp" %>
