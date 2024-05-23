
<%@page import="models.Commande"%>
<%@page import="repositories.CommandeRepository"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setAttribute("pageTitle", "Voir Commande");
request.setAttribute("isHeaderDisabled", true);

List<String> cssFiles = new ArrayList<>();


cssFiles.add("css/voirCommande.css");
request.setAttribute(AbstractServlet.CSS_FILES_KEY, cssFiles);
%>
<%@ include file="../template/start.jsp" %>
<h1>Commandes</h1>
<table class="commandes-table">
    <thead>
        <tr>
            <th>ID</th>
            <th>Date de Création</th>
            <th>Date de Récupération</th>
            <th>Détails</th>
        </tr>
    </thead>
    <tbody>
<%
CommandeRepository commandeRepository = new CommandeRepository();
List<Commande> commandes = commandeRepository.findAll();
if (commandes != null) {
                for (Commande commande : commandes) {
        %>
        <tr>
            <td><%= commande.getId() %></td>
            <td><%= commande.getDateCreation() %></td>
            <td><%= commande.getDateArrivee() %></td>
            <td><a href='CommandDetailsServlet?id=<%= commande.getId() %>'><button >Détails</button></a></td>
        </tr>
        <%
                }
            }
        %>
    </tbody>
</table>



<%@ include file="../template/end.jsp" %>
