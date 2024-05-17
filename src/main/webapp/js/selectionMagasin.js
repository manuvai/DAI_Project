function handleCheckboxSelection(checkbox) {
    const checkboxes = document.querySelectorAll('.checkbox-magasin');
    checkboxes.forEach(function(item) {
        if (item !== checkbox) {
            item.checked = false;
        }
    });

   


