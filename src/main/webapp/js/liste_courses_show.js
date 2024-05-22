
$(window).on('load', () => {
	
	$('.js-handleArticlesProposal').click(function() {
		let postItId = $(this).find("input[type=hidden][name=postIt-id]").val();
		let postItLabel= $(this).find("input[type=hidden][name=postIt-label]").val();
		let modal = $('#js-modalArticlesProposal')
		
		let rootPath = $('#rootPath').val();
		let q = encodeURIComponent(postItLabel);
		
		let url = `${rootPath}/articles/search?q=${q}`;
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

function createCardElement(dto, postItId, rootPath) {
	let listeId = $('#listeId').val();
	
	const cardElement = document.createElement('div');
	cardElement.className = 'card';
	cardElement.style.width = '18rem';
	
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

function replacePostIt(postItId, articleId, listeId) {
	console.log(postItId);
	console.log(articleId);
	let rootPath = $('#rootPath').val();
	
	postItId = encodeURIComponent(postItId)
	articleId = encodeURIComponent(articleId)
	
	let url = `${rootPath}/post-its?action=replace&post-it-id=${postItId}&article-id=${articleId}&liste-id=${listeId}`
	
	post(url, xhr => {
		console.log()
	})
}

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