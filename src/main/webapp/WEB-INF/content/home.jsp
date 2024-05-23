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
 <div id="rayonCarousel" class="carousel slide" data-ride="carousel">
    <div class="carousel-inner">
        <% 
        List<Rayon> rayons = (List<Rayon>) request.getAttribute("rayons");
        if (rayons != null) {
            for (int i = 0; i < rayons.size(); i += 5) {
        %>
        <div class="carousel-item <%= (i == 0) ? "active" : "" %>">
            <div class="d-flex justify-content-center flex-wrap">
                <% 
                for (int j = i; j < Math.min(i + 5, rayons.size()); j++) {
                    Rayon rayon = rayons.get(j);
                %>
                <div class="flex-item">
                    <button onclick="window.location.href='Catalogue?nomRayon=<%=rayon.getNomRayon() %>';" 
                            class="btn btn-primary btn-rayon" id="<%= rayon.getId() %>">
                           
                        <%= rayon.getNomRayon() %>
                    </button>
                </div>
                <% 
                }
                %>
            </div>
        </div>
        <% 
            }
        } 
        %> 
    </div>
    <a class="carousel-control-prev" href="#rayonCarousel" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Précédent</span>
    </a>
    <a class="carousel-control-next" href="#rayonCarousel" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Suivant</span>
    </a>
</div>


<h2>Nos articles en promotion</h2>
<%@ include file="./commons/article_caroussel.jsp" %>

<%@ include file="../template/end.jsp" %>
