<%@page import="java.util.Objects"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! @SuppressWarnings("unchecked") %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="images/logo-supermarket.png" type="image/x-icon"> 
<link rel="stylesheet" type="text/css" href="css/header.css">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<title>Online Shop</title>
</head>
<body>
   <header class="main-header">
   <img class="logo" src="images/logo-supermarket.png">
        <nav class="icon-nav">
         	<a href="#" class="icon" ><i class="fas fa-store" title="Choisis ton magasin"></i></a>
            <input id="search-input" type="text" class="search-input" placeholder="Search..">
            <a href="#" class="icon"><i class="fas fa-shopping-cart" title="Panier"></i></a>
            <a href="#" class="icon"><i class="fas fa-user"  title="Connexion"></i></a>
        </nav>
    </header>
