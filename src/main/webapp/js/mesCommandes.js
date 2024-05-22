
document.addEventListener("DOMContentLoaded", function () {
    const selectElement = document.getElementById("etatFiltre");

    selectElement.addEventListener("change", function () {
        const selectedEtat = this.value;
        const commandesRows = document.querySelectorAll("#commandes tbody tr");

        commandesRows.forEach(function (row) {
            const etatCell = row.querySelector("td:nth-child(2)");
            const etatText = etatCell.textContent.trim();

            if (selectedEtat === "Tous" || etatText === selectedEtat) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });
    });
});

