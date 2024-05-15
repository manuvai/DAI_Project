<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/catalogue.css">
    <%@ page import="models.*" %>
    <%@ page import="java.util.List" %>
<div id="catalogue">
<% if (request.getAttribute("articles") != null) {%>
            <% for (Article article : (List<Article>)request.getAttribute("articles")) {%>
                <a href=""> <div class="article" style="background-image:url('images/pommes.jpg');">
                	<span class ="nomArticle"><%= article.getLib() %></span><br/>
        			<span class ="descArticle"><%= article.getPrixUnitaire() %>€</span><br/>
    				<span class ="poidsArticle"><%= article.getPoids()%>g</span><br/>
                </div> </a>
            <% }
	}
%>
</div>
