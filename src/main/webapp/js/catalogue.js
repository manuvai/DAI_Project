/*
0 : nom
1 : img
2 : prix
3 : poids
4 : bio
5 : promotion
6 : id
7 : nbrArticlePanier
*/
$("#sousCategories").change(function() {
	$.ajax({
        url: "CatalogueAjaxServlet?sousCategorie="+document.getElementById('sousCategories').value,
        type: 'GET',
        success: function(res){
			refreshCatalogue(res);
		}
    });
});

$("#categories").change(function() {	
	$.ajax({
        url: "CatalogueAjaxServlet?categorie="+document.getElementById('categories').value,
        type: 'GET',
        success: function(res){
	refreshCatalogue(res);
	refreshSousCategories(res);
	}
    });
});

function refreshSousCategories(res){
	
	let sousCats = res.getElementsByTagName("sousCategorie");
	let divSousCats = document.getElementById("sousCategories");
	divSousCats.innerHTML="";
	for(i=0; i<sousCats.length; i++){
		let sousCat = sousCats[i];
		console.log(sousCat.children[0].textContent);
		let divOption = document.createElement("option");
		divOption.className = "categorie";
		divOption.value = sousCat.children[0].textContent;
		divOption.textContent = sousCat.children[0].textContent;
		divSousCats.append(divOption);
	}
}

function refreshCatalogue(res){
	let catalogue = document.getElementById("catalogue");
	catalogue.innerHTML ="";
	
	let articles = res.getElementsByTagName("article");
	let br = document.createElement("br");
	
	for(i=0; i<articles.length; i++){
		
		let article = articles[i];
		
		//div article
		let divArticle = document.createElement("div");
		divArticle.className="article";
		catalogue.append(divArticle);
		divArticle = document.querySelectorAll(".article")[document.querySelectorAll(".article").length-1];
		
		//Lien
		let lienArticle = document.createElement("a");
		lienArticle.className = "lienArticle";
		lienArticle.href = "Article?idArticle="+article.children[6].textContent;
		divArticle.append(lienArticle);
		lienArticle = document.querySelectorAll(".lienArticle")[document.querySelectorAll(".lienArticle").length-1];
				
		//Bio
		if(article.children[4]){
			let imgBio = document.createElement("img");
			imgBio.className = "img-bio-catalogue";
			imgBio.src = "images/bio.png";
			lienArticle.append(imgBio);
		}
		
		//Image
		let imgArticle = document.createElement("img");
		imgArticle.className="imgArticle";
		imgArticle.src = article.children[1].textContent;
		lienArticle.append(imgArticle);
		
		//Détails
		let detailsArticle = document.createElement("div");
		detailsArticle.className="articleDetails";
		lienArticle.append(detailsArticle);
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
			
			//Panier
			let divPanier = document.createElement("div");
			divPanier.className="gestionPanier";
			divArticle.append(divPanier)
			divPanier = document.querySelectorAll(".gestionPanier")[document.querySelectorAll(".gestionPanier").length-1];
			
				let quantitePanier = document.createElement("span");
				quantitePanier.id="article"+article.children[6].textContent;
				quantitePanier.textContent=article.children[7].textContent;
				divPanier.append(quantitePanier);
				
				let boutonEnlever = document.createElement("i");
				boutonEnlever.className="boutonPanier fas fa-minus icon";
				boutonEnlever.id = "enleverButton";
				boutonEnlever.onclick=function(){enleverAuPanier(article.children[6].textContent)};
				boutonEnlever.title="moins";
				divPanier.prepend(boutonEnlever);
					
				let boutonAjouter = document.createElement("i");
				boutonAjouter.id = "ajouterButton";
				boutonAjouter.className="boutonPanier boutonPanier fas fa-plus icon";
				boutonAjouter.onclick=function(){ajouterAuPanier(article.children[6].textContent)};
				boutonAjouter.title="plus";
				divPanier.append(boutonAjouter);
			
			}
}