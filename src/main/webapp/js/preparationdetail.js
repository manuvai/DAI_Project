var start = document.getElementById("bu-start");
var stop = document.getElementById("bu-stop");
var annuler = document.getElementById("bu-annuler");

var isStarted = false;
var checkboxes = document.querySelectorAll('.checkbox');
var startTime;
var endTime;

annuler.disabled = true;
stop.disabled = true;
checkboxes.forEach(function(checkbox) {
    checkbox.disabled = true;
});


start.addEventListener("click", function() {
    start.disabled = true;
    annuler.disabled = false;
    isStarted = true;
    startTime = Date.now();
    
    checkboxes.forEach(function(checkbox) {
        checkbox.disabled = false;
    });
});

annuler.addEventListener("click", function() {
    start.disabled = false;
    annuler.disabled = true;
    isStarted = false;
    
    checkboxes.forEach(function(checkbox) {
        checkbox.disabled = true;
    });
});



stop.addEventListener("click", function() {
	endTime = Date.now();
	console.log((endTime - startTime) / 1000)
	stop.disabled = true
	annuler.disabled = true
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