
$(window).on('load', () => {
	$('form.js-handleQtyChange').submit(function() {
		let articleId = $(this).find("input[name=article-id]").val();
		
		let qtyElement = $(this).find("input[name=qty]");
		
		let actualQty = $(`#article-${articleId} .js-articleQty`).val();
		
		qtyElement.val(actualQty);
		
		return true;
	});
	
	$('.js-handleArticlesProposal').click(function() {
		let postItId = $(this).find("input[type=hidden][name=postIt-id]").val();
		let postItLabel= $(this).find("input[type=hidden][name=postIt-label]").val();
		let modal = $('#js-modalArticlesProposal')
		
		let rootPath = $('#rootPath').val();
		let q = encodeURIComponent(postItLabel);
		
		let url = `${rootPath}/search?q=${q}`;
		get(url, xhr => {
			let responseXML = xhr.responseXML;
			
			let nuplets = responseXML.getElementsByTagName("article");
			
			let html = ""
			$('.modal-body').html(html);
			
			if (!nuplets || nuplets.length <= 0) {
				$('.modal-body').text(`Aucun article correspondant à "${postItLabel}"`);
				
			} else {
				Array.from(nuplets).forEach(item => {
					dto = xmlToDto(item);
					
					let cardElement = createCardElement(dto, postItId, rootPath);
					
					$('.modal-body').append(cardElement);
				})
			}
			
			modal.modal('show');
		})
	})
	
})

/**
 * Création d'un élément card par postIt
 */
function createCardElement(dto, postItId, rootPath) {
	let listeId = $('#listeId').val();
	
	const cardElement = document.createElement('div');
	cardElement.className = 'card col-4';
	
	const imgElement = document.createElement('img');
	imgElement.className = 'card-img-top';
	imgElement.src = `${rootPath}/${dto['imagePath']}`;
	imgElement.alt = `Image ${dto['nom']}`;
	
	const cardBodyElement = document.createElement('div');
	cardBodyElement.className = 'card-body';
	
	const cardTextElement = document.createElement('p');
	cardTextElement.className = 'card-text';
	cardTextElement.textContent = dto['nom'];
	
	const cardFooterElement = document.createElement('div');
	cardFooterElement.className = 'card-footer';
	
	const formElement = document.createElement('form');
	formElement.action = `${rootPath}/listes_courses/show?action=replacePostIt&id=${listeId}&postIt-id=${postItId}&article-id=${dto['id']}`
	formElement.method = 'POST';
	
	const buttonElement = document.createElement('button');
	buttonElement.type = 'submit';
	buttonElement.className = 'btn btn-primary js-handleReplacePostIt';
	
	const inputElement = document.createElement('input');
	inputElement.type = 'hidden';
	inputElement.name = 'article-id';
	inputElement.value = dto['nom'];
	
	const postItInputElement = document.createElement('input');
	postItInputElement.type = 'hidden';
	postItInputElement.name = 'postIt-id';
	postItInputElement.value = postItId;
	
	buttonElement.appendChild(document.createTextNode('Remplacer par cet article'));
	
	formElement.appendChild(inputElement);
	formElement.appendChild(postItInputElement);
	formElement.appendChild(buttonElement)
	
	cardFooterElement.appendChild(formElement);
	
	cardBodyElement.appendChild(cardTextElement);
	cardElement.appendChild(imgElement);
	cardElement.appendChild(cardBodyElement);
	cardElement.appendChild(cardFooterElement);
	
	return cardElement;
}

/**
 * Transformation d'un noeud XML article en dictionnaire JS.
 */
function xmlToDto(xmlNode) {
	let result = {};
	
	if (xmlNode) {
		
		let elementsKeys = ['id', 'nom', 'imagePath'];
		
		elementsKeys.forEach(el => {
			result[el] = xmlNode.getElementsByTagName(el)[0].innerHTML
		})
		
	}
	
	return result;
}

/**
 * Méthode pour effectuer un appel HTTP GET
 */
function get(url, onSuccess) {
    ajax("GET", url, onSuccess);
}

/**
 * Méthode pour effectuer un appel HTTP POST
 */
function post(url, onSuccess) {
    ajax("POST", url, onSuccess);

}

/**
 * Implémentation des appels AJAX
 */
function ajax(verb, url, onSuccess, onFail = null) {
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
