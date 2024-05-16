var start = document.getElementById("bu-start");
var stop = document.getElementById("bu-stop");
var checkboxes = document.querySelectorAll('.checkbox');
var startTime;
var endTime;

start.addEventListener("click", function() {
    start.style.display = "none";
    console.log("test");
    startTime = Date.now();
});

stop.addEventListener("click", function() {
	endTime = Date.now();
	console.log((endTime - startTime) / 1000)
});

checkboxes.forEach(function(checkbox) {
    checkbox.addEventListener('change', function() {
        var toutesCoches = Array.from(checkboxes).every(function(cb) {
            return cb.checked;
        });

        if (toutesCoches) {
            stop.style.display = 'block';
        } else {
            stop.style.display = 'none';
        }
    });
});