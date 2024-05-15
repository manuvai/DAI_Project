<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% request.setAttribute("pageTitle", "HomePage"); %>

<%@ include file="../template/head.jsp" %>
<link rel="stylesheet" type="text/css" href="css/superMarket.css">
<%@ page import="models.Rayon" %>
<%@ page import="java.util.List" %>
<h2>Rayons</h2>
<div id="rayon">
   
    <% 
        List<Rayon> rayons = (List<Rayon>) request.getAttribute("rayons");
        if (rayons != null) {
            for (Rayon rayon : rayons) {
    %>
                <button class="btn-rayon" id=<%= rayon.getId() %>> <%= rayon.getNomRayon() %></button>
    <% 
            }
        } 
    %>
            
</div>

<div id="couverture">
</div>

<div id="promotions">
<h2>Promotions</h2>
</div>
<%@ include file="../template/tail.jsp" %>
<%@ include file="../template/footer.jsp" %>
