document.addEventListener("DOMContentLoaded", () => {
	const checkbox = document.getElementById("utiliserPoints");
    checkbox.addEventListener("change", () => {
		

    const totalPP = document.getElementById("totalPP");
    const ptFidel = parseInt(document.getElementById("ptFidel").textContent);
	const total = parseFloat(totalPP.textContent);
	console.log(totalPP, total)
	const reduction = Math.floor(ptFidel/10);
		
        if (checkbox.checked) {
            const nouveauTotal = total - parseFloat(reduction);
            totalPP.textContent = nouveauTotal; 
        } else {          
            const ancienTotal = total + parseFloat(reduction);
            totalPP.textContent = ancienTotal; 
        }
    });
});
