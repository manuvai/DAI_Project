<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "HomePage"); %>
<%! @SuppressWarnings("unchecked") %>

<%@page import="java.util.Objects"%>
<%@ page import="models.Magasin" %>
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
jsFiles.add("js/selectionMagasin.js");

request.setAttribute(AbstractServlet.JS_LIBS_KEY, jsFiles);
%>
<%@ include file="../template/start.jsp" %>
<h2>Choisis ton magasin de retrait</h2>

<div id="magasin">
  <form action="MagasinServlet" method="post">
        <div>
            <% 
                List<Magasin> magasins = (List<Magasin>) request.getAttribute("magasins");
                if (magasins != null) {
                    for (Magasin magasin : magasins) {
            %>
            
            <div class="magasin-container">
               
                <label for="<%= magasin.getCodeMagasin() %>">
                   <input class="checkbox-magasin" type="radio" id="<%= magasin.getCodeMagasin() %>" name="magasinSelectionne" value="<%= magasin.getCodeMagasin() %>"  required>
                    <strong><%= magasin.getNomMagasin() %></strong> 
                    <br>
                     <i class="fas fa-location-arrow icon"></i>    <%= magasin.getAdresseMagasin() %>
                    <br>
                     <i class="fas fa-clock icon"></i>    <%= magasin.getHorairesMagasin() %>
                </label>
            </div>
            <% 
                    }
                } 
            %>   
        </div>
        <button id="submit-button" class="btn-rayon" type="submit" >Choisir ce magasin </button>
    </form>
</div>


<%@ include file="../template/end.jsp" %>
