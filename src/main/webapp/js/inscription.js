document.addEventListener("DOMContentLoaded", () => {

	document.getElementById("mdp").addEventListener("change",validation);
	document.getElementById("confirmMdp").addEventListener("change",validation);
	document.getElementById("email").addEventListener("change",validation);
	document.getElementById("emailConfirm").addEventListener("change",validation);

});

function validation ()
	{
	var mdp = document.getElementById("mdp").value.trim();
    var confirmMdp = document.getElementById("confirmMdp").value.trim();
    var email = document.getElementById("email").value.trim();
    var confirmEmail = document.getElementById("confirmEmail").value.trim();
        
    var submitButton = document.getElementById('submitButton');
	if (mdp !== '' && confirmMdp !== '' && email !== '' && confirmEmail !== '') {
            if (mdp === confirmMdp && email === confirmEmail) {
                submitButton.disabled = false;
            } else {
                submitButton.disabled = true;
            }
        } else {
            submitButton.disabled = true;
        }
	}
