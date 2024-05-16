document.addEventListener("DOMContentLoaded", () => {

	document.getElementById("mdp").addEventListener("input",validation);
	document.getElementById("confirmMdp").addEventListener("input",validation);
	document.getElementById("email").addEventListener("input",validation);
	document.getElementById("confirmEmail").addEventListener("input",validation);

});

function validation ()
	{
	var mdp = document.getElementById("mdp").value.trim();
    var confirmMdp = document.getElementById("confirmMdp").value.trim();
    var email = document.getElementById("email").value.trim();
    var confirmEmail = document.getElementById("confirmEmail").value.trim();
    var messageElement = document.getElementById('messageInformatif')
    var submitButton = document.getElementById('submitButton');
	if (mdp !== '' && confirmMdp !== '' && email !== '' && confirmEmail !== '') {
		messageElement.textContent = "";
            if (mdp === confirmMdp && email === confirmEmail) {
                submitButton.disabled = false;
            } else {
				console.log(email,confirmEmail)
				if(mdp != confirmMdp){
					console.log("mdp inco")
					messageElement.textContent = "Mot de passe non identique";
				}
				if(email != confirmEmail){
					console.log("pb mail")
					if(messageElement.textContent === ""){
						console.log("mdp ok")
						messageElement.textContent = "Mail non identique";
					}else{
						console.log("mdp pas ok")
						messageElement.textContent = messageElement.textContent + " et Mail non identique"
					}
				}
                submitButton.disabled = true;
            }
        } else {
            submitButton.disabled = true;
        }
	}
