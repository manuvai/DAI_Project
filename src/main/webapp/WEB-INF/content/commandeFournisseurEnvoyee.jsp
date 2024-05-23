
<%@ page import="java.util.ArrayList"%>
<%@ page import="repositories.ArticleRepository"%>
<%@ page import="servlets.AbstractServlet"%>

<%
List<String> cssFiles = new ArrayList<>();
cssFiles.add("css/commandeFournisseurValidee.css");
request.setAttribute(AbstractServlet.CSS_FILES_KEY, cssFiles);

%>
<%@ include file="../template/start.jsp"%>

<body>
<div class="confirmation-container">
    <p>Votre commande a bien été envoyée. Elle porte le numéro <strong><%= session.getAttribute("idLastCmd") %></strong></p>
    <a href="voirCommande"><button>Voir toutes les commandes</button></a>
</div>
</body>
</html>

<%@ include file="../template/footer.jsp"%>