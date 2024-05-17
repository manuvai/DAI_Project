<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.List"%>
<%@page import="dtos.ArticleStockDto"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Map.Entry"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 

List<ArticleStockDto> articles = (List<ArticleStockDto>) request.getAttribute("articles");

for (ArticleStockDto dto : articles) { %>

<tr>
  	<th scope="row"><%= dto.getId() %></th>
  	<td><%= dto.getLabel() %></td>
  	<td class="col-md-2">
  		<img src="<%= request.getContextPath() %>/<%= dto.getImagePath() %>" 
  			alt="<%= dto.getLabel() %>" 
  			class="img-thumbnail">
	</td>
  	<td>
  		<% 
			final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
  			SortedSet<Date> sortedKeys = new TreeSet<Date>(dto.getStocks().keySet());
  			List<String> keys = sortedKeys.stream()
  					.map(key -> {
  						return formatter.format(key);
  					}).toList();
  			List<String> values = sortedKeys.stream()
	  				.map(key -> dto.getStocks().get(key))
	  				.map(count -> Integer.toString(count))
	  				.toList();
  		%>
  		<input type=hidden class="hidden-data-canva-key" value="<%= dto.getId() %>">
  		<input type=hidden id="hidden-keys-<%= dto.getId() %>" value="<%= String.join(",", keys) %>">
  		<input type=hidden id="hidden-values-<%= dto.getId() %>" value="<%= String.join(",", values) %>">
  		<canvas id="stock-canva-<%= dto.getId() %>"></canvas>
  	</td>
</tr>

<% } %>
