<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "HomePage"); %>
<%@page import="java.util.Objects"%>
<%! @SuppressWarnings("unchecked") %>

<%@ page import="models.Magasin" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>

<link rel="icon" href="images/logo-supermarket.png" type="image/x-icon"> 
<link rel="stylesheet" type="text/css" href="css/header.css">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<title>Online Shop </title>

  <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/superMarket.css">
</head>
<body>
<%@ include file="../template/head.jsp" %>


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
                   <input class="checkbox-magasin" type="radio" id="<%= magasin.getCodeMagasin() %>" name="magasinSelectionne" value="<%= magasin.getNomMagasin() %>" onclick="handleCheckboxSelection(this)" required>
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


<script src="js/selectionMagasin.js"></script>
<%@ include file="../template/footer.jsp" %>

<!-- jQuery and Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>



