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