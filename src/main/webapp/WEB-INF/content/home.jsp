<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Objects"%>
<%! @SuppressWarnings("unchecked") %>

<%@ page import="models.Rayon" %>
<%@ page import="models.Article" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>

<link rel="icon" href="images/logo-supermarket.png" type="image/x-icon"> 
<link rel="stylesheet" type="text/css" href="css/header.css">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<title>Online Shop</title>

  <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/superMarket.css">
</head>
<script src="js/home.js"></script>
<body>
<%@ include file="../template/head.jsp" %>


<h2>Nos rayons</h2>
<div id="rayon">
    <% 
        List<Rayon> rayons = (List<Rayon>) request.getAttribute("rayons");
        if (rayons != null) {
            for (Rayon rayon : rayons) {
    %>
                <button onclick="window.location.href='Catalogue?nomRayon=<%=rayon.getNomRayon() %>';" class="btn-rayon" id=<%= rayon.getId() %>> <%= rayon.getNomRayon() %></button>
    <% 
            }
        } 
    %>       
</div>

<div id="couverture-catalogue">
</div>

<h2>Nos articles en promotion</h2>
<div class="container">
 <a class="carousel-control-prev" href="#articleCarousel" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          
        </a>
    <div id="articleCarousel" class="carousel slide" data-ride="carousel">
        <div class="carousel-inner">
        
            <% int itemCount = 0;
           	   List<Article> articles = (List<Article>) request.getAttribute("articles");
               for (int i = 0; i < articles.size(); i += 4) { %>
                <div class="carousel-item <% if (i == 0) { %>active<% } %>">
                    <div class="row">
                        <% for (int j = i; j < Math.min(i + 4, articles.size()); j++) { %>
                            <div class="col-md-3">
                                <div class="card">
                                    <div class="card-body">
                                        <img class="img-item" src=<%= articles.get(j).getCheminImage() %> >
                                        <p class="card-text"><%= articles.get(j).getDesc() %></p>
                                        <img class="img-nutriscore" src="<%= "images/nutriscores/" + articles.get(j).getNutriscore() + ".png" %>" >
                                        <div>
                                        	<p class="price"> <%= articles.get(j).getPrixUnitaire() %> €</p>
                                        	<i id="enleverButton" class="fas fa-arrow-alt-circle-left ison" onclick="enleverAuPanier('<%= articles.get(j).getId() %>')" title="moins"></i>
                                        	<span id="article<%= articles.get(j).getId() %>">
                                        	<% 
									            Integer nbr = (Integer) session.getAttribute(articles.get(j).getId().toString());
												 if (nbr != null ){%>
												<%= nbr %>
												 <%} else {%>
												 0
													 <% }%>
				 </span>
                                        	  <i id="ajouterButton" class="fas fa-arrow-alt-circle-right icon" title="plus" onclick="ajouterAuPanier('<%= articles.get(j).getId() %>')"></i>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    </div>
                </div>
                <% itemCount += 4;
               } %>
        </div>
       
        
    </div>
    <a class="carousel-control-next" href="#articleCarousel" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
         
        </a>
</div>


<%@ include file="../template/footer.jsp" %>
</body>
</html>
<!-- jQuery and Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>

