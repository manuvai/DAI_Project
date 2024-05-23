
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

<h2>Habitudes de consommation</h2>
<div class="container mb-2">
	<div class="graphDash row d-flex justify-content-center">
		<div class="bio col-lg-3 col-lg-6">
		<p>		Répartion des articles selon s'ils sont bios</p>
			<canvas  id="bioChart"></canvas>
		</div>
		<div class="nutriscore col-lg-3 col-lg-6">
		<p>		Répartion des articles selon leur nutriscore</p>
			<canvas  id="nutriChart"></canvas>
		</div>
		<div class="cats col-lg-3 col-lg-6">
		<p>		Répartion des articles selon leurs catégories</p>
		<canvas  id="catsChart"></canvas>
		</div>
	</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
  const ctx = document.getElementById('bioChart');
  const bioArt = <%= session.getAttribute("bioArticle") %>;
  const nonBioArt = <%= session.getAttribute("nonBioArticle") %>;
  new Chart(ctx, {
	type: 'doughnut',
    data: {
      labels: ['Bio', 'Non Bio'],
      datasets: [{
        data: [bioArt,nonBioArt],
        backgroundColor: [
            'rgb(255, 99, 132)',
            'rgb(54, 162, 235)',
          ],
          hoverOffset: 4
      }]
    },
    
  });
  
  const ctxNutri = document.getElementById('nutriChart');
  const a = <%= session.getAttribute("A") %>;
  const b = <%= session.getAttribute("B") %>;
  const c = <%= session.getAttribute("C") %>;
  const d = <%= session.getAttribute("D") %>;
  const e = <%= session.getAttribute("E") %>;
  new Chart(ctxNutri, {
	type: 'doughnut',
    data: {
      labels: ['A', 'B', 'C', 'D', 'E'],
      datasets: [{
        data: [a,b,c,d,e],
        hoverOffset: 4
      }]
    },
    
  });
  
  const ctxCate = document.getElementById('catsChart');
  new Chart(ctxCate, {
	type: 'doughnut',
    data: {
      labels: <%= session.getAttribute("nomCats") %>,
      datasets: [{
        data: <%= session.getAttribute("numCats") %>,
        hoverOffset: 4
      }]
    },
    
  });
</script>

<%@ include file="../../template/end.jsp" %>
