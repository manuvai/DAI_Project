<%@page import="java.util.Objects"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! @SuppressWarnings("unchecked") %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>
	<%= Objects.isNull(request.getAttribute("pageTitle")) ? "Site marchand" : request.getAttribute("pageTitle") %>
</title>
</head>
<body>
<div class="content container">
