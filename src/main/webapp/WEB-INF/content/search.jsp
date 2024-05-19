<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
boolean isListEmpty = request.getAttribute("articlesCaroussel") == null 
	|| ((List<Article>) request.getAttribute("articlesCaroussel")).isEmpty();

%>
<%@ include file="../template/start.jsp" %>
<h2>Articles correspondant à &quot;<%= request.getParameter("query") %>&quot;</h2>

<%
if (isListEmpty) {
%>
Aucun article trouvé.
<%
} else {
%>

<%@ include file="./commons/article_caroussel.jsp" %>

<%
}
%>
<%@ include file="../template/end.jsp" %>
    
