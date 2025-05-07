document.addEventListener('DOMContentLoaded', function() {
    // Éléments DOM
    const achatForm = document.getElementById('purchaseForm');
    const achatsTable = document.getElementById('purchasesTable');
    const resetBtn = document.getElementById('resetBtn');

    // URLs API
    const apiUrl = 'http://localhost:8080/api/achats'; // Changé pour correspondre à votre table
    const fournisseursUrl = 'http://localhost:8080/api/fournisseurs';
    let fournisseurs = [];
    let editingId = null;

    // Chargement initial
    loadFournisseurs().then(loadPurchases).catch(handleError);

    // Fonction pour charger les fournisseurs
    function loadFournisseurs() {
        return fetch(fournisseursUrl)
            .then(response => {
                if (!response.ok) throw new Error(`Erreur HTTP! Statut: ${response.status}`);
                return response.json();
            })
            .then(data => {
                if (!Array.isArray(data)) throw new Error("Les données reçues ne sont pas un tableau");

                fournisseurs = data;
                const select = document.getElementById('fournisseurId');
                select.innerHTML = '<option value="">Sélectionner un fournisseur</option>';

                data.forEach(f => {
                    const option = document.createElement('option');
                    option.value = f.id;
                    option.textContent = f.nom;
                    select.appendChild(option);
                });
            });
    }

    // Fonction pour charger les achats
    function loadPurchases() {
        fetch(apiUrl)
            .then(response => {
                if (!response.ok) throw new Error(`Erreur HTTP! Statut: ${response.status}`);
                return response.json();
            })
            .then(data => {
                if (!Array.isArray(data)) throw new Error("Les données reçues ne sont pas un tableau");

                achatsTable.innerHTML = '';

                data.forEach(achat => {
                    const fournisseur = fournisseurs.find(f => f.id === achat.fournisseurId) || { nom: 'Inconnu' };
                    const dateAchat = achat.dateAchat ? new Date(achat.dateAchat) : new Date();

                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${fournisseur.nom}</td>
                        <td>${dateAchat.toLocaleDateString()}</td>
                        <td>${achat.montantTotal?.toFixed(2) || '0.00'} €</td>
                        <td>${achat.modePaiement || 'Non spécifié'}</td>
                        <td>${achat.statut || 'En attente'}</td>
                        <td>${achat.notes || ''}</td>
                        <td>
                            <button onclick="editPurchase('${achat.id}')" class="btn btn-sm btn-warning">Modifier</button>
                            <button onclick="deletePurchase('${achat.id}')" class="btn btn-sm btn-danger">Supprimer</button>
                        </td>
                    `;
                    achatsTable.appendChild(row);
                });
            })
            .catch(handleError);
    }

    // Gestion de la soumission du formulaire
    achatForm.addEventListener('submit', function(e) {
        e.preventDefault();

        const achatData = {
            fournisseurId: parseInt(document.getElementById('fournisseurId').value),
            montantTotal: parseFloat(document.getElementById('montantTotal').value),
            modePaiement: document.getElementById('modePaiement').value,
            statut: document.getElementById('statut').value,
            notes: document.getElementById('notes').value
        };

        const method = editingId ? 'PUT' : 'POST';
        const url = editingId ? `${apiUrl}/${editingId}` : apiUrl;

        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(achatData)
        })
            .then(response => {
                if (!response.ok) throw new Error('Erreur lors de la requête');
                return response.json();
            })
            .then(() => {
                resetForm();
                loadPurchases();
            })
            .catch(handleError);
    });

    // Fonction pour éditer un achat
    window.editPurchase = function(id) {
        fetch(`${apiUrl}/${id}`)
            .then(response => {
                if (!response.ok) throw new Error('Achat non trouvé');
                return response.json();
            })
            .then(achat => {
                document.getElementById('fournisseurId').value = achat.fournisseurId;
                document.getElementById('montantTotal').value = achat.montantTotal;
                document.getElementById('modePaiement').value = achat.modePaiement;
                document.getElementById('statut').value = achat.statut;
                document.getElementById('notes').value = achat.notes || '';

                editingId = id;
                window.scrollTo({ top: 0, behavior: 'smooth' });
            })
            .catch(handleError);
    };

    // Fonction pour supprimer un achat
    window.deletePurchase = function(id) {
        if (confirm('Êtes-vous sûr de vouloir supprimer cet achat ?')) {
            fetch(`${apiUrl}/${id}`, {
                method: 'DELETE'
            })
                .then(response => {
                    if (!response.ok) throw new Error('Erreur lors de la suppression');
                    loadPurchases();
                })
                .catch(handleError);
        }
    };

    // Réinitialisation du formulaire
    function resetForm() {
        achatForm.reset();
        editingId = null;
    }

    // Gestion des erreurs
    function handleError(error) {
        console.error('Erreur:', error);
        alert(`Une erreur est survenue: ${error.message}`);
    }

    // Bouton reset
    if (resetBtn) {
        resetBtn.addEventListener('click', resetForm);
    }
});