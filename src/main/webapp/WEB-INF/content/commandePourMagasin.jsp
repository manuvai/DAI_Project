<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="models.Magasin"%>
<%@page import="models.Article"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Faire Une Commande</title>
</head>
<%
List<String> cssFiles = new ArrayList<>();
cssFiles.add("css/commandePourMagasin.css");
request.setAttribute(AbstractServlet.CSS_FILES_KEY, cssFiles);
%>
<%@ include file="../template/start.jsp"%>
<body>
	<form action="ValidationCommandeServlet" method="post">
		<button id="submit-button" class="btn-rayon" type="submit">Commander
		</button>
		<div>
			<h3>Choisissez un magasin</h3>
			<%
			List<Magasin> magasins = (List<Magasin>) session.getAttribute("magasinsPourCommande");
			if (magasins != null) {
				for (Magasin magasin : magasins) {
					String codeMagasinStr = String.valueOf(magasin.getCodeMagasin());
					boolean isChecked = magasins != null && magasins.equals(codeMagasinStr);
			%>

			<div class="magasin-container">
				<label for="<%=magasin.getCodeMagasin()%>"> <input
					class="checkbox-magasin" type="radio"
					id="<%=magasin.getCodeMagasin()%>" name="magasinSelectionne"
					value="<%=magasin.getCodeMagasin()%>"
					<%=isChecked ? "checked" : ""%> required> <strong><%=magasin.getNomMagasin()%></strong>
					<br> <i class="fas fa-location-arrow icon"></i> <%=magasin.getAdresseMagasin()%>
					<br> <i class="fas fa-clock icon"></i> <%=magasin.getHorairesMagasin()%>
				</label>
			</div>
			<%
			}
			}
			%>
		</div>
		<div>
			<h3>Choisissez une date de livrfaison</h3>
			<input type="date" id="date" name="date" required>
		</div>
		<div>
			<h3>Choisissez les articles</h3>
			<table>
				<tr>
					<th>Article</th>
					<th>Prix</th>
					<th>Quantité</th>
				</tr>
				<%
				List<Article> articles = (List<Article>) session.getAttribute("articlesPourCommande");
				if (articles != null) {
					for (Article article : articles) {
				%>
				<tr>
					<td><%=article.getLib()%></td>
					<td><%=article.getPrixUnitaire()%> €</td>
					<td><input type="number" name="quantite_<%=article.getId()%>"
						min="0" value="0"></td>
				</tr>
				<%
				}
				}
				%>
			</table>

		</div>


	</form>
	<script>
    document.addEventListener("DOMContentLoaded", function() {
        const dateInput = document.getElementById('date');
        const today = new Date().toISOString().split('T')[0];
        dateInput.setAttribute('min', today);
    });
</script>
</body>
</html>