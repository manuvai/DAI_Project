
<%@page import="dtos.ListeCourseDto"%>
<%@page import="models.ListeDeCourse"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="servlets.AbstractServlet"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
List<ListeCourseDto> listes = (List<ListeCourseDto>) request.getAttribute("listes");
Integer addedListeId = (Integer) request.getAttribute("addedListeId");
%>
<%@ include file="../../template/start.jsp"%>

<h1>Recettes disponibles</h1>
	<%
	if (listes == null || listes.isEmpty()) {
	%>
	<div class="row col-12">
		Votre liste est vide.
	</div>
	<%
	} else {
	%>

	<table class="table mt-4 col-12">
		<thead>
			<tr>
				<th scope="col">Nom de la recette</th>
				<th scope="col">Nombre d'éléments</th>
				<th scope="col">Actions</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (ListeCourseDto liste : listes) {
				boolean isAdded = liste.getId().equals(addedListeId);
			%>

			<tr <%= isAdded ? "class=\"table-success\"" : "" %>>
				<td><%=liste.getNom()%></td>
				<td><%=liste.getNbElements()%> éléments</td>
				<td>
					<a
						href="<%=request.getContextPath()%>/RecettesServlet/show?id=<%=liste.getId()%>"
						class="btn btn-secondary">
						Voir les ingrédients
					</a> 
					
					<form method="POST"
						action="<%=request.getContextPath()%>/RecettesServlet?action=transformToPanier&id=<%=liste.getId()%>">
						<button type="submit" class="btn btn-primary">Copier la recette</button>
					</form>
				</td>
			</tr>

			<%
			}
			%>
		</tbody>
	</table>

	<%
	}
	%>
</div>

<%@ include file="../../template/end.jsp"%>
