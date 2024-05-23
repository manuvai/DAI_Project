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
jsFiles.add("js/mesCommandes.js");

request.setAttribute(AbstractServlet.JS_LIBS_KEY, jsFiles);
%>
<%@ include file="../template/start.jsp" %>
<h2>Mes commandes</h2>

<div id="commandes" class="container">
	
	<select id="etatFiltre"class="form-select"> 
	    <option value="Tous">TOUS</option>
	    <option value="ATTENTE">ATTENTE</option>
	    <option value="VALIDEE">VALIDEE</option>
	    <option value="PRETE">PRETE</option>
	    <option value="LIVRE">LIVRE</option>
	</select>
	
	<br>
	<br>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>État de la commande</th>
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
                    <tr id="<%= commande.getId() %>">
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
                        <td><%= commande.getCreneau().getMagasin().getNomMagasin() %>
                        <br><%= commande.getCreneau().getMagasin().getAdresseMagasin() %>
                        <br><%= commande.getCreneau().getDateCreneau().toString() %> <%= commande.getCreneau().getHeureCreneau().name().substring(1).replace("_", " à ") %></td>
                       <td>
						    <form action="./DetailsCommandeClientServlet" method="get">
						        <input type="hidden" name="idCommande" value="<%= commande.getId() %>">
						        <button type="submit" class="btn btn-primary"> 
							        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-eye">
									    <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z">
									    </path>
									    <circle cx="12" cy="12" r="3"></circle>
									 </svg> Voir les articles
								 </button>
						    </form>
						 	<br>
						 	<% if (commande.getEtat() != Panier.Etat.LIVRE){ %>
						 	<form action="./CreneauClientServlet" method="get">
						 	 	<input type="hidden" name="idCommande" value="<%= commande.getId() %>">
						 		<button type="submit" class="btn btn-primary">
							    	<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit">
										  <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
										  <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L7 20.5l-4 1 1-4L18.5 2.5z"></path>
									</svg> Modifier créneau
						        </button>
						 	</form>
						 	<%} %>
						 	
						   
						    	
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
