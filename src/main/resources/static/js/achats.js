document.addEventListener('DOMContentLoaded', function() {
    const achatForm = document.getElementById('achatForm');
    const achatsTable = document.getElementById('achatsTable').querySelector('tbody');
    const resetBtn = document.getElementById('resetBtn');

    let fournisseurs = [];

    // Load Fournisseurs with error handling
    fetch('/api/fournisseurs')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (!Array.isArray(data)) {
                throw new Error("Expected an array of fournisseurs");
            }
            fournisseurs = data;
            const select = document.getElementById('fournisseur');
            select.innerHTML = '<option value="">Sélectionner un fournisseur</option>';
            data.forEach(f => {
                const option = document.createElement('option');
                option.value = f.id;
                option.textContent = f.nom;
                select.appendChild(option);
            });
            loadAchats(); // Load achats only after fournisseurs are loaded
        })
        .catch(error => {
            console.error("Failed to load fournisseurs:", error);
            alert("Erreur lors du chargement des fournisseurs. Voir la console.");
        });

    // Rest of your code...
});