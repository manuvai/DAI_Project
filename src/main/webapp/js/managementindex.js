$(document).on('change', '.custom-file-input', function (event) {
	let files = event.target.files;
	names = [];
	for (let i = 0; i < files.length; i++) {
		names.push(files[i].name);
	}
	
	if (names.length == 0) {
		names.push("Choisissez les images et fichiers CSV");
	}
	
	let displayName = names.join(', ');
	
	if (displayName.length >= 50) {
		names = [names.length + " fichiers fournis"];
	}
	displayName = names.join(', ');
    $(this).next('.custom-file-label').html(displayName);
})
