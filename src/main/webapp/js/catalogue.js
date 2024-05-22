/*
0 : nom
1 : img
2 : prix
3 : poids
4 : bio
5 : promotion
*/

$("#categories").change(function() {
	$.ajax({
        url: "CatalogueAjaxServlet?categorie="+document.getElementById('categories').value,
        type: 'GET',
        success: function(res){
	refreshCatalogue(res);
	}
    });
});

function refreshCatalogue(res){
	let catalogue = document.getElementById("catalogue");
	catalogue.innerHTML ="";
	
	let articles = res.getElementsByTagName("article");
	let br = document.createElement("br");
	
	for(i=0; i<articles.length; i++){
		
		let divArticle = document.createElement("div");
		divArticle.className="article";
		catalogue.append(divArticle);
		divArticle = document.querySelectorAll(".article")[document.querySelectorAll(".article").length-1];
		console.log(divArticle);
		let article = articles[i];
		
		//Bio
		if(article.children[4]){
			let imgBio = document.createElement("img");
			imgBio.className = "img-bio-catalogue";
			imgBio.src = "images/bio.png";
			divArticle.append(imgBio);
		}
		
		//Image
		let imgArticle = document.createElement("img");
		imgArticle.className="imgArticle";
		imgArticle.src = article.children[1].textContent;
		divArticle.append(imgArticle);
		
		//Détails
		let detailsArticle = document.createElement("div");
		detailsArticle.className="articleDetails";
		divArticle.append(detailsArticle);
		detailsArticle = document.querySelectorAll(".articleDetails")[document.querySelectorAll(".articleDetails").length-1];
			//Nom
			let spanNomArticle = document.createElement("span");
			spanNomArticle.className="nomArticle";
			spanNomArticle.textContent=article.children[0].textContent;
			detailsArticle.append(spanNomArticle);
			detailsArticle.append(br);
			//Prix
			let prixContainer = document.createElement("div");
			prixContainer.className="price-container";
			detailsArticle.append(prixContainer);
			prixContainer = document.querySelectorAll(".price-container")[document.querySelectorAll(".price-container").length-1];
				//Promotion
				if(article.children[2].textContent!=article.children[5].textContent){
					//prix normal
					let prixInitial = document.createElement("span");
					prixInitial.className="price promotion";
					prixInitial.textContent=article.children[2].textContent+"€";
					prixContainer.append(prixInitial);
					//prix réduit
					let prixReduit = document.createElement("span");
					prixReduit.className="price discount";
					prixReduit.textContent=parseFloat(article.children[5].textContent).toFixed(2)+"€";
					prixContainer.append(prixReduit);
				}
				else{
					let prix = document.createElement("span");
					prix.className="price";
					prix.textContent = article.children[2].textContent+"€";
					prixContainer.append(prix);
				}
			detailsArticle.append(prixContainer);
			//Poids
			let spanPoids = document.createElement("span");
			spanPoids.className="poidsArticle";
			spanPoids.textContent = article.children[3].textContent+"g";
			detailsArticle.append(spanPoids);
			}
}