var start = document.getElementById("bu-start");
var stop = document.getElementById("bu-stop");
var annuler = document.getElementById("bu-annuler");

var isStarted = false;
var checkboxes = document.querySelectorAll('.checkbox');
var startTime;
var endTime;


var idPanierRaw = document.getElementById("affichageCommande").textContent;
var regex = /\d+/; 
var idPanier = texte.match(regex);

//Etats initiaux
annuler.disabled = true;
stop.disabled = true;
checkboxes.forEach(function(checkbox) {
    checkbox.disabled = true;
});

//Gestion du bouton démarer
start.addEventListener("click", function() {
    start.disabled = true;
    annuler.disabled = false;
    isStarted = true;
    startTime = Date.now();

    checkboxes.forEach(function(checkbox) {
        checkbox.disabled = false;
    });
    	enregistrerTempsBd(endTime, "Debut")

});

//Gestion du bouton annuler
annuler.addEventListener("click", function() {
    start.disabled = false;
    stop.disabled = true;
    annuler.disabled = true;
    isStarted = false;

    checkboxes.forEach(function(checkbox) {
        checkbox.disabled = true;
        checkbox.checked = false;
    });
});

stop.addEventListener("click", function() {
	endTime = Date.now();
	stop.disabled = true
	annuler.disabled = true
	enregistrerTempsBd(endTime, "Fin")
});

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


function enregistrerTempsBd(temps, etat) {

	var xhr = new XMLHttpRequest();
	
	    // Requête au serveur avec les paramètres éventuels.
	    xhr.open("GET","PreparationDateCommandeServlet?idPanier="+idPanier+"&Date"+etat+"=true"+temps);
	
        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	    /*
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
	        }; */
	    xhr.send();
}