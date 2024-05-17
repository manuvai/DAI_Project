document.addEventListener("DOMContentLoaded", () => {

});

function ajouterAuPanier(articleId) {

	var nbrPanierElement = document.getElementById("nbrPanier");
    var nbrPanier = parseInt(nbrPanierElement.textContent.trim(), 10);
    

    var nbrArticleElement = document.getElementById("article"+articleId);
    var nbrArticle = parseInt(nbrArticleElement.textContent.trim(), 10);
    
    var nbrPrixElement = document.getElementById("prix"+articleId);
    var nbrPrix = parseInt(nbrPrixElement.textContent.trim(), 10);
    
    var prixElement = document.getElementById("prixUnitaire"+articleId);
  	var prix = parseFloat(prixElement.textContent.trim());
  	
  	var promoElement = document.getElementById("promotion"+articleId);
  	var promo = 0;
  	
  	var totalElement = document.getElementById("total");
  	var total= parseFloat(totalElement.textContent.trim());

  	if(promoElement === null){
		  promo = 0;

	 }else {
 
  		promo = parseFloat(promoElement.textContent.trim());
	  }

    
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
		    nbrPrixElement.innerText = nbrArticle*prix*(1-promo/100) +"€";
		    totalElement.innerText = total + prix*(1-promo/100);
			}
		};
	xhr.send();

}

function enleverAuPanier(articleId) {
    var nbrPanierElement = document.getElementById("nbrPanier");
    var nbrPanier = parseInt(nbrPanierElement.textContent.trim(), 10);
    
    var nbrArticleElement = document.getElementById("article"+articleId);
    var nbrArticle = parseInt(nbrArticleElement.textContent.trim(), 10);
    	var promoElement = document.getElementById("promotion"+articleId);
  	var promo = 0;

  	if(promoElement === null){
		  promo = 0;

	 }else {
 
  		promo = parseFloat(promoElement.textContent.trim());
	  }
    if(nbrArticle>0){
		var nbrPrixElement = document.getElementById("prix"+articleId);
	    var nbrPrix = parseInt(nbrPrixElement.textContent.trim(), 10);
	    
	    var prixElement = document.getElementById("prixUnitaire"+articleId);
	  	var prix = parseFloat(prixElement.textContent.trim());
  	
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
				    nbrPrixElement.innerText = nbrArticle*prix*(1-promo/100) +"€";
					}
				};
			xhr.send();
	}

    nbrPanierElement.innerText = nbrPanier;
    nbrArticleElement.innerText = nbrArticle;
}