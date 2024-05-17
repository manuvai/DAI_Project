document.addEventListener("DOMContentLoaded", () => {

});

function ajouterAuPanier(articleId) {

	var nbrPanierElement = document.getElementById("nbrPanier");
    var nbrPanier = parseInt(nbrPanierElement.textContent.trim(), 10);
    

    var nbrArticleElement = document.getElementById("article"+articleId);
    var nbrArticle = parseInt(nbrArticleElement.textContent.trim(), 10);
    
    nbrArticle++;
    nbrPanier++;
    
    var xhr = new XMLHttpRequest();

	// Requête au serveur avec les paramètres éventuels.
	xhr.open("GET","GestionPanier?idArticle="+articleId+"&ajouter=true");

	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function()
		{
		// Si la requête http s'est bien passée.
		if (xhr.status === 200)
			{			    
		    nbrPanierElement.innerText = nbrPanier;
		    nbrArticleElement.innerText = nbrArticle;
			}
		};
	xhr.send();

}

function enleverAuPanier(articleId) {
    var nbrPanierElement = document.getElementById("nbrPanier");
    var nbrPanier = parseInt(nbrPanierElement.textContent.trim(), 10);
    
    var nbrArticleElement = document.getElementById("article"+articleId);
    var nbrArticle = parseInt(nbrArticleElement.textContent.trim(), 10);
    console.log(nbrArticle)
    if(nbrArticle>0){
		nbrPanier--;
		nbrArticle--;
		
		    var xhr = new XMLHttpRequest();

			// Requête au serveur avec les paramètres éventuels.
			xhr.open("GET","GestionPanier?idArticle="+articleId+"&ajouter=false");
		
			// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
			xhr.onload = function()
				{
				// Si la requête http s'est bien passée.
				if (xhr.status === 200)
					{			    
				    nbrPanierElement.innerText = nbrPanier;
				    nbrArticleElement.innerText = nbrArticle;
					}
				};
			xhr.send();
	}

    nbrPanierElement.innerText = nbrPanier;
    nbrArticleElement.innerText = nbrArticle;
}