/**
 * 
 */
$(window).on('load', () => {
    let headerRootPath = $('#headerRootPath').val();
    $('#searchInput').on('input', function () {
		return;
		// FIXME Corriger le CSS pour permettre un affichage ergonomique de la liste des résultats 
        let query = encodeURIComponent($(this).val());

        if (query.length <= 2) {
            $('#searchResults').hide();
            
        } else {
        	let url = `${headerRootPath}/articles/search?q=${query}`;
        	
        	get(url, xhr => {
                
				let responseXML = xhr.responseXML;
				let nuplets = responseXML.getElementsByTagName("article");
				
				if (nuplets.length <= 0) {
					return;
				}

                $('#searchResults').empty().show();
                Array.from(nuplets).forEach(item => {
                    let idArticle = item.getElementsByTagName("id")[0].innerHTML;
                    let nomArticle = item.getElementsByTagName("nom")[0].innerHTML;
                    let link = `${headerRootPath}/Article?idArticle=${idArticle}`;
                    
                    let toAppend = `<a class="dropdown-item" href="${link}">${nomArticle}</a>`;
                    
                    $('#searchResults').append(toAppend);
                });
			});
        }
    });

    $(document).click(function (e) {
        if (!$(e.target).closest('#searchForm').length) {
            $('#searchResults').hide();
        }
    });
});

function get(url, onSuccess) {
    ajax("GET", url, onSuccess);
}

function post(url, onSuccess) {
    ajax("POST", url, onSuccess);

}

function ajax(verb, url, onSuccess, onFail = null) {
    // Objet XMLHttpRequest.
    let xhr = new XMLHttpRequest();

    xhr.open(verb, url);
    if ("POST" == verb) {
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    }

    xhr.onload = () => {
        if (xhr.status === 200) {
            onSuccess(xhr);
        }
    };
    xhr.send();
}

function processKey ()
	{
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();

	// Récupération de la chaîne de caractères dans la zone de recherche.
	var ch = document.getElementById("searchInput").value;

	if (ch.length !== 0)
		{
		// Requête au serveur avec les paramètres éventuels.
		var param = "pattern=" + encodeURIComponent(ch);
		var url = "SearchArtServlet";

		xhr.open("POST",url);
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

		// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
		xhr.onload = function()
			{
			// Si la requête http s'est bien passée.
			if (xhr.status === 200)
				{
				// Réponse du serveur.
				var l_articles = xhr.responseXML.getElementsByTagName("article");

				// Elément html que l'on va mettre à jour.
				var elt = document.getElementById("searchResults");
				elt.innerHTML = "";
				for (var i=0; i<l_articles.length; i++){
					console.log( l_articles[i].getElementsByTagName("lib")[0])
					elt.insertAdjacentHTML("beforeend","<a href=\"Article?idArticle="+l_articles[i].getElementsByTagName("id")[0].firstChild.nodeValue+"\"><div class=\"cde\">" +  l_articles[i].getElementsByTagName("lib")[0].firstChild.nodeValue + "</div>");
}
				// Visibilité du block d'affichage de la réponse.
				if (l_articles.length !== 0)
					document.getElementById("searchResults").style.display = "flex";
				else
					document.getElementById("searchResults").style.display = "none";

				}
			};

		// Envoi de la requête.
		xhr.send(param);
		}
	else
		// Cache la zone d'affichage de la réponse.
		document.getElementById("searchResults").style.display = "none";
	}
document.addEventListener("DOMContentLoaded", () => {
	document.getElementById("searchInput").addEventListener("input",processKey);});