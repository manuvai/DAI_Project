document.addEventListener("DOMContentLoaded", () => {
	document.getElementById("categorie").addEventListener("change",categorieChange);
});

function categorieChange(){
	console.log("OUAIS");
	nomCategorie = getElementById("categorie").value;
	$.ajax({
		url: 'CatalogueAjaxServlet',
		type: 'GET',
		data: {
			categorie:nomCategorie
		},
		dataType: 'text',

		success: function(response) {
			$("#zone").html(text);
		},
		error: function(xhr) {
		    console.log("ERREUR : "+xhr);
		}
	});

}
