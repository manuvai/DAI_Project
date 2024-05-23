var start = document.getElementById("bu-start");
var stop = document.getElementById("bu-stop");
var annuler = document.getElementById("bu-annuler");

var isStarted = false;
var isModified = false;
var checkboxes = document.querySelectorAll('.checkbox');
var startTime;
var endTime;

var idPanierRaw = document.getElementById("affichageCommande").textContent;
var regex = /\d+/; 
var idPanier = idPanierRaw.match(regex);

var boutonsPM = document.getElementsByClassName("boutonPanier");

//Etats initiaux
annuler.disabled = true;
stop.disabled = true;
checkboxes.forEach(function(checkbox) {
    checkbox.disabled = true;
});

var initialDisplay = window.getComputedStyle(boutonsPM[0]).display;

for (var i = 0; i < boutonsPM.length; i++) {
    boutonsPM[i].style.display = "none";
}

//Gestion du bouton démarer
start.addEventListener("click", function() {
    start.disabled = true;
    annuler.disabled = false;
    isStarted = true;
    startTime = Date.now();
    for (var i = 0; i < boutonsPM.length; i++) {
        boutonsPM[i].style.display = initialDisplay;
    }

    checkboxes.forEach(function(checkbox) {
        checkbox.disabled = false;
    });
    	enregistrerTempsBd(startTime, "Debut")

});

//Gestion du bouton annuler
annuler.addEventListener("click", function() {
    start.disabled = false;
    stop.disabled = true;
    annuler.disabled = true;
    isStarted = false;
    for (var i = 0; i < boutonsPM.length; i++) {
        boutonsPM[i].style.display = "none";
    }

    checkboxes.forEach(function(checkbox) {
        checkbox.disabled = true;
        checkbox.checked = false;
    });
});


//Gestion du bouton terminer la commande
stop.addEventListener("click", function() {
	endTime = Date.now();
	stop.disabled = true
	annuler.disabled = true
    for (var i = 0; i < boutonsPM.length; i++) {
        boutonsPM[i].style.display = "none";
    }

	enregistrerTempsBd(endTime, "Fin")
});

//Gestion de l'état des boutons en fonction des checkboxs
checkboxes.forEach(function(checkbox) {
    checkbox.addEventListener('change', function() {
        var toutesCoches = Array.from(checkboxes).every(function(cb) {
            return cb.checked;
        });

        if (toutesCoches && isStarted) {
            stop.disabled = false;
        } else {
            stop.disable = true;
        }
    });
});

/*******************
 * 
 * Changements en bd
 * 
 ******************/

function ajouterArticle(idArticle, idPanier) {
    var quantiteElement = document.getElementById("quantite" + idArticle);
    var quantite = parseInt(quantiteElement.textContent, 10);

    quantite++;

    var xhr = new XMLHttpRequest();

    xhr.open("GET", "GestionPreparation?idArticle=" + idArticle + "&idPanier=" + idPanier + "&quantite=" + quantite);

    xhr.onload = function() {
        if (xhr.status === 200) {
            quantiteElement.innerText = quantite;
        }
    };

    xhr.send();
}

function enleverArticle(idArticle, idPanier) {
    var quantiteElement = document.getElementById("quantite" + idArticle);
    var quantite = parseInt(quantiteElement.textContent, 10);

    if (quantite > 0) {
        quantite--;
    }

    var xhr = new XMLHttpRequest();

    xhr.open("GET", "GestionPreparation?idArticle=" + idArticle + "&idPanier=" + idPanier + "&quantite=" + quantite);

    xhr.onload = function() {
        if (xhr.status === 200) {
            quantiteElement.innerText = quantite;
        }
    };

    xhr.send();
}



function enregistrerTempsBd(temps, etat) {

	var xhr = new XMLHttpRequest();
	    // Requête au serveur avec les paramètres éventuels.
	    xhr.open("GET","PreparationDateCommandeServlet?idPanier="+idPanier+"&Date"+etat+"="+temps);
	    xhr.send();
	    if (etat == "Fin"){
	    	window.location.replace("./PreparationCommandesServlet" + "?idCommande=" + idPanier)
	    }
}