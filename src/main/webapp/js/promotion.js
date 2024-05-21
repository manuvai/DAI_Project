document.addEventListener("DOMContentLoaded", () => {
	const checkbox = document.getElementById("utiliserPoints");
    checkbox.addEventListener("change", () => {
		

    	const totalPP = document.getElementById("totalPP");
    	const ptFidel = parseInt(document.getElementById("ptFidel").textContent.trim(), 10);
    	let ptUsedElement = document.getElementById("ptUsed");
    	let ptUsed = parseInt(ptUsedElement.textContent.trim(), 10);
        let totalText = totalPP.textContent.trim().replace(',', '.');
        let total = parseFloat(totalText);
        let reduction = Math.floor(ptFidel / 10);
    
		
        if (checkbox.checked) {
			if (reduction > total){
				reduction = Math.floor(total);
			}
            const nouveauTotal = total - reduction;
            totalPP.textContent = nouveauTotal.toFixed(2);
            ptUsedElement.textContent = reduction*10;
        } else {   			    
            const ancienTotal = total + ptUsed/10;
            totalPP.textContent = ancienTotal.toFixed(2);
            ptUsedElement.textContent = 0;
        }
    });
});
