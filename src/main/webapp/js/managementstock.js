
document.addEventListener("DOMContentLoaded", () => {

	document.getElementById("magasin-id")
		.addEventListener("change", redirect);

	document.querySelectorAll('.hidden-data-canva-key')
		.forEach((el) => chargerCanva(el.value))
});

function redirect() {
	let rootPath = document.getElementById('rootPath').value;
	let magasinId = document.getElementById("magasin-id").value;
	
	let nextLink = rootPath + "/management/stock?magasin-id=" + magasinId;
	window.location.href = nextLink;
}

function chargerCanva(id) {
	if (!id) {
		return;
	}
	
	let canva = document.getElementById('stock-canva-' + id)
	let dataKeysString = document.getElementById('hidden-keys-' + id).value
	let dataValuesString = document.getElementById('hidden-values-' + id).value
	
	let labels = dataKeysString.split(',')
	let data = dataValuesString.split(',').map(el => parseInt(el))
	
	let ctx = canva.getContext('2d')
	
	new Chart(ctx, {
		type: 'line',
		options: {
	        maintainAspectRatio: false,
	        scales: { 
	            yAxes: [{ 
	                ticks: { 
	                    beginAtZero:true 
	                } 
	            }] 
        	} 
	    },
		data: {
			labels: labels,
			datasets: [
				{
					label: 'stock',
					data: data,
				    backgroundColor: "rgba(153,205,1,0.6)",
				},
			]
		}
	})
	
}
