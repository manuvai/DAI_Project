<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! @SuppressWarnings("unchecked") %>

<%@page import="java.util.Objects"%>
<%@ page import="models.Rayon" %>
<%@ page import="models.Article" %>
<%@ page import="java.util.List" %>
<%

List<String> jsFiles = new ArrayList<>();
jsFiles.add("js/home.js");
request.setAttribute(AbstractServlet.JS_FILES_KEY, jsFiles);

%>
<%@ include file="../template/start.jsp" %>
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
<%@ include file="./commons/article_caroussel.jsp" %>

<%@ include file="../template/end.jsp" %>
