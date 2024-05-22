<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "HomePage"); %>
<%! @SuppressWarnings("unchecked") %>

<%@page import="java.util.Objects"%>
<%@ page import="models.Panier" %>
<%@ page import="java.util.List" %>
<%
// =============================
// GESTION DES FICHIERS JS
// =============================
List<String> jsFiles = (List<String>) request.getAttribute(AbstractServlet.JS_FILES_KEY);

jsFiles = jsFiles == null ? new ArrayList<>() : jsFiles;
/*
	Cette partie traite l'ajout des CDN des librairies JS
	Pour ajouter une nouvelle librairie :
		jsFiles.add("js/example.js");
*/

request.setAttribute(AbstractServlet.JS_LIBS_KEY, jsFiles);
%>
<%@ include file="../template/start.jsp" %>
<h2>Mes commandes</h2>

<div id="commandes" class="container">
    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>État de la commande</th>
                <th>Date de la commande</th>
                <th>Informations de retrait </th>
                <th></th>
                
                
            </tr>
        </thead>
        <tbody>
            <% 
            List<Panier> commandes = (List<Panier>) request.getAttribute("commandes");
            if (commandes != null) {
                for (Panier commande : commandes) {
                	
            %>
                    <tr>
                        <td><%= commande.getId() %></td>
                         <td>
                            <% 
                            Panier.Etat etat = commande.getEtat();
                            String badgeClass = "";
                            switch (etat) {
                                case ATTENTE:
                                    badgeClass = "badge bg-warning text-dark";
                                    break;
                                case VALIDEE:
                                    badgeClass = "badge bg-primary";
                                    break;
                                case PRETE:
                                    badgeClass = "badge bg-info text-dark";
                                    break;
                                case LIVRE:
                                    badgeClass = "badge bg-success";
                                    break;
                                default:
                                    badgeClass = "badge bg-secondary";
                                    break;
                            }
                            %>
                            <span class="<%= badgeClass %>"><%= etat.name() %></span>
                        </td>
                        <td><%= commande.getDateDebutPreparation() %>
                        </td>
                        <td><%= commande.getCreneau().getMagasin().getNomMagasin() %>
                        <br><%= commande.getCreneau().getMagasin().getAdresseMagasin() %>
                        <br><%= commande.getCreneau().getDateCreneau().toString() %> <%= commande.getCreneau().getHeureCreneau().name().substring(1).replace("_", " à ") %></td>
                       <td>
						    <form action="./DetailsCommandeClientServlet" method="get">
						        <input type="hidden" name="idCommande" value="<%= commande.getId() %>">
						        <button type="submit" class="btn btn-primary">Voir les articles</button>
						    </form>
						</td>

                        
                    </tr>
            <% 
                }
            } 
            %>  
        </tbody>
    </table>
</div>



<%@ include file="../template/end.jsp" %>
