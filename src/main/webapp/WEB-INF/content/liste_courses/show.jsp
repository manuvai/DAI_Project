<%@page import="models.PostIt"%>
<%@page import="dtos.ListeCourseDto"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

ListeCourseDto liste = (ListeCourseDto) request.getAttribute("liste");
Map<PostIt, Integer> postItsMap = liste.getPostsItsMap();

%>
<%@ include file="../../template/start.jsp"%>

<h1>Liste : <%= liste.getNom() %></h1>

<div class="row">

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
			<form method="POST" 
				action="<%= request.getContextPath() %>/listes_courses/show?id=<%=liste.getId()%>&action=add">
				<tr>
					<td>
					</td>
					<td>
						<input type="text" 
							name="postItName" 
							id="postItName" 
							class="form-control"
							aria-describedby="postItNameHelp" 
							placeholder="Entrez le nom de la nouvelle postIt" />
						<small id="postItNameHelp" 
							class="form-text text-muted">
							Exemple : Beurre
						</small>
					</td>
					<td>
						<input type="number" 
							name="qty" 
							id="qty" 
							class="form-control"
							aria-describedby="qtyHelp" 
							placeholder="Entrez la quantité de la nouvelle postIt" />
						<small id="qtyHelp" 
							class="form-text text-muted">
							Exemple : 5
						</small>
					</td>
					<td>
						<button type="submit" class="btn btn-success">Ajouter</button>
					</td>
				</tr>
			</form>
			<%
			for (Entry<PostIt, Integer> entry : postItsMap.entrySet()) {
				PostIt postIt = entry.getKey();
				Integer qty = entry.getValue();
				boolean isAdded = false;
			%>

			<tr <%= isAdded ? "class=\"table-success\"" : "" %>>
				<th scope="row"><%= postIt.getIdPostIt() %></th>
				<td><%= postIt.getLabel() %></td>
				<td><%= qty %></td>
				<td>
					<form action="<%= request.getContextPath() %>/liste_courses/show?action=delete"
						method="post">
						<input type="hidden" name="liste-courses-id"
							value="<%= postIt.getIdPostIt() %>" />
						<button type="submit" disabled class="btn btn-danger">Supprimer</button>
					</form>
				</td>
			</tr>

			<%
			}
			%>
		</tbody>
	</table>

</div>

<%@ include file="../../template/end.jsp"%>
