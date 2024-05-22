<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!@SuppressWarnings("unchecked")%>

<%@page import="java.util.Objects"%>
<%@ page import="models.Article"%>
<%@ page import="java.util.List"%>
<%
List<String> jsFiles = new ArrayList<>();
jsFiles.add("js/home.js");
request.setAttribute(AbstractServlet.JS_FILES_KEY, jsFiles);

List<String> cssFiles = (List<String>) request.getAttribute(AbstractServlet.CSS_FILES_KEY);

cssFiles = cssFiles == null ? new ArrayList<>() : cssFiles;
/*
	Cette partie traite l'ajout des CDN des librairies JS
	Pour ajouter une nouvelle librairie :
		cssFiles.add("css/example.css");
*/
cssFiles.add("css/dashboard.css");

request.setAttribute(AbstractServlet.CSS_LIBS_KEY, cssFiles);

%>

<%@ include file="../../template/start.jsp"%>

<h2>Articles commandés le plus fréquemment</h2>
<% if (request.getAttribute("articlesCaroussel") == null || ((List<Article>) request.getAttribute("articlesCaroussel")).isEmpty()) { %>
	<div class="container">
		Cette liste est vide. <a class="text-mute" href="<%= request.getContextPath() %>/home">Voir quoi ajouter ?</a>
	</div>
<% } else { %>
<%@ include file="../commons/article_caroussel.jsp"%>
<% } %>

<div>
	<h2>Habitudes de consommation</h2>
	
	<div class="graphDash">
	<div class="bio">
	<p>		Répartion des articles selon s'ils sont bios</p>
		<canvas  id="bioChart"></canvas>
	</div>
	<div class="nutriscore">
	<p>		Répartion des articles selon leur nutriscore</p>
		<canvas  id="nutriChart"></canvas>
	</div>
	<div class="cats">
	<p>		Répartion des articles selon leurs catégories</p>
	<canvas  id="catsChart"></canvas>
	</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	
<script>
  const ctx = document.getElementById('bioChart');
  const bioArt = <%= session.getAttribute("bioArticle") %>
  const nonBioArt = <%= session.getAttribute("nonBioArticle") %>
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
  const a = <%= session.getAttribute("A") %>
  const b = <%= session.getAttribute("B") %>
  const c = <%= session.getAttribute("C") %>
  const d = <%= session.getAttribute("D") %>
  const e = <%= session.getAttribute("E") %>
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
</div>

<%@ include file="../../template/end.jsp"%>
