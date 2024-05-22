const canvas = document.getElementById('bioChart');
const graph = new Chart(canvas, {
	type: 'pie',
	data: {
		labels: ['bio', 'non bio'],
		datasets: [{
			data: [54,46]
		}]
	},
	options: {}
})