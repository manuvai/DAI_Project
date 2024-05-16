function handleCheckboxSelection(checkbox) {
            var checkboxes = document.querySelectorAll('.checkbox-magasin');
            checkboxes.forEach(function(item) {
                if (item !== checkbox) {
                    item.checked = false;
                }
            });
        }