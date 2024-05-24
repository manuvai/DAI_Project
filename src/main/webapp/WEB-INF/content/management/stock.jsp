
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="dtos.ArticleStockDto"%>
<%@page import="java.util.List"%>
<%@page import="models.Magasin"%>

<% 
request.setAttribute("pageTitle", "Gestion des articles"); 

List<String> jsLibs = new ArrayList<>();
jsLibs.add("https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.2.2/Chart.min.js");
request.setAttribute(AbstractServlet.JS_LIBS_KEY, jsLibs);

List<String> jsFiles = new ArrayList<>();
jsFiles.add("js/managementstock.js");
request.setAttribute(AbstractServlet.JS_FILES_KEY, jsFiles);
%>

<%@ include file="../../template/start.jsp" %>

<h1>Visualisation des stocks</h1>
<a href="<%= request.getContextPath() %>/management/">Retour à la page d'accueil</a>
<div class="container">
    <div class="form-group">
        <label for="magasin-id">Chosissez un magasin :</label>
        <select class="form-control" id="magasin-id" name="magasin-id">
            <option value="" <%= request.getParameter("magasin-id") != null ? "" : "selected" %>>
                Choisir un magasin
            </option>
            <% 
            if (request.getAttribute("magasins") != null) {
                for (Magasin magasin : (List<Magasin>) request.getAttribute("magasins"))  {
                    boolean isSelected = Integer.toString(magasin.getCodeMagasin()).equals(request.getParameter("magasin-id"));
            %>
            <option value="<%= magasin.getCodeMagasin()%>" <%= isSelected ? "selected" : "" %>>
                <%= magasin.getNomMagasin() %>
            </option>
            <%
                }
            }
            %>
        </select>
    </div>
</div>

<% if (request.getAttribute("articles") != null && ((List<ArticleStockDto>) request.getAttribute("articles")).size() > 0) { %>
	
	<h2>Articles</h2>
  <table class="table">
    <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">Nom</th>
        <th scope="col">Image</th>
        <th scope="col">Stock</th>
      </tr>
    </thead>
    <tbody>
	
    <%@ include file="article_rows.jsp" %>
    </tbody>
  </table>
	
<% } %>
</div>
<input type="hidden" id="rootPath" value="<%= request.getContextPath() %>">

<%@ include file="../../template/end.jsp" %>
