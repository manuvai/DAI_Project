<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "HomePage"); %>
<%! @SuppressWarnings("unchecked") %>

<%@page import="java.util.Objects"%>
<%@ page import="models.Panier" %>
<%@ page import="models.Creneau" %>
<%@ page import="java.util.List" %>
<%@page import="repositories.CreneauRepository" %>
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
<%Panier panier = (Panier) request.getAttribute("panier");
List<Creneau> creneauxMagasin = (List<Creneau>) request.getAttribute("creneauxMagasin");
CreneauRepository cr = new CreneauRepository();
%>

<h2>Formulaire de modification du creneau de retrait de la commande <%= "N° " + request.getAttribute("idCommande") %></h2>

	 <div class="container mt-5">
        <div class="card">
           
            <div class="card-body">
                <form action="CreneauClientServlet" method="post">
                    <div class="form-group">
                        <label for="etatCommande"><strong>État de la commande :</strong></label>
                        <p id="etatCommande" class="form-control-plaintext">  <% 
                            Panier.Etat etat = panier.getEtat();
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
                            <span class="<%= badgeClass %>"><%= etat.name() %></span></p>
                    </div>
                    <div class="form-group">
                        <label for="magasinRetrait"><strong>Magasin de retrait :</strong></label>
                        <p id="magasinRetrait" class="form-control-plaintext">
                        <%= panier.getCreneau().getMagasin().getNomMagasin() %><br>
                        <%= panier.getCreneau().getMagasin().getAdresseMagasin() %>
                        </p>
                    </div>
                    <div class="form-group">
                        <label for="creneauActuel"><strong>Créneau actuel : </strong></label>
                        <p id="creneauActuel" class="form-control-plaintext">
                            <%= panier.getCreneau().getDateCreneau().toString() %> <%= panier.getCreneau().getHeureCreneau().name().substring(1).replace("_", " à ") %>
                        </p>
                    </div>
                    <div class="form-group">
                        <label for="creneau"><strong>Sélectionnez un nouveau créneau : </strong></label>
                        <select name="creneau" id="creneau" class="form-control">
                            <% 
                                Creneau creneauActuel = panier.getCreneau();
                                for (Creneau creneau : creneauxMagasin) { 
                                    boolean isSelected = creneau.equals(creneauActuel);
                            %>
                                <option value="<%= creneau.getCodeCreneau() %>" <%= isSelected ? "selected" : "" %>>
                                    <%= creneau.getHeureCreneau().toString().replace("_","-").substring(1) %> 
                                    le 
                                    <%= creneau.getDateCreneau() %> 
                                    (<%= cr.findDisposParCreneau(creneau) %> places disponibles)
                                </option>
                            <% 
                                } 
                            %>
                        </select>
                    </div>
                    <input type="hidden" name="panierId" value="<%= panier.getId() %>">
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Modifier créneau</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
	
	
<br>
  <a href="<%= request.getContextPath() %>/CommandesClientServlet"><button  class="btn btn-primary">Retour sur mes commandes</button></a>

<%@ include file="../template/end.jsp" %>
