
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

<h1>Vos listes de courses</h1>

<div class="row">
	<div class="row col-12 mb-4">
		<form class="col-12" method="POST">
			<div class="form-group col-9">
				<label for="listeName">Une nouvelle liste en tête ?</label> 
				<input type="text" 
					class="form-control" 
					id="listeName"
					name="listeName"
					aria-describedby="listeNameHelp" 
					placeholder="Entrez le nom de la nouvelle liste" />
				<small id="listeNameHelp" 
					class="form-text text-muted">
					Exemple : Liste hebdomadaire...
				</small>
			</div>
			<button type="submit" class="btn btn-primary col-3">Ajouter</button>
		</form>
	</div>
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
				<th scope="col">#</th>
				<th scope="col">Nom de la liste</th>
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
				<th scope="row"><%=liste.getId()%></th>
				<td><%=liste.getNom()%></td>
				<td><%=liste.getNbElements()%> éléments</td>
				<td><a
					href="<%=request.getContextPath()%>/liste_courses/show?id=<%=liste.getId()%>"
					class="btn btn-secondary disabled">Voir la liste</a> <a
					href="<%=request.getContextPath()%>/liste_courses/edit?id=<%=liste.getId()%>"
					class="btn btn-primary disabled">Modifier</a>
					<form action="<%=request.getContextPath()%>/liste_courses/delete"
						method="post">
						<input type="hidden" name="liste-courses-id"
							value="<%=liste.getId()%>" />
						<button type="submit" disabled class="btn btn-danger">Supprimer</button>
					</form></td>
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
