document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('medicament-form');
    const cancelBtn = document.getElementById('cancel-btn');
    let isEditing = false;
    let currentEditId = null;

    // Charger les médicaments au démarrage
    loadMedicaments();

    // Gestion de la soumission du formulaire
    form.addEventListener('submit', function(e) {
        e.preventDefault();

        const medicament = {
            codeBarre: document.getElementById('code-barre').value,
            nom: document.getElementById('nom').value,
            dci: document.getElementById('dci').value,
            dosage: document.getElementById('dosage').value,
            formeGalenique: document.getElementById('forme-galenique').value,
            classeTherapeutique: document.getElementById('classe-therapeutique').value,
            laboratoire: document.getElementById('laboratoire').value,
            prixAchat: parseFloat(document.getElementById('prix-achat').value),
            prixVente: parseFloat(document.getElementById('prix-vente').value),
            tauxRemboursement: parseFloat(document.getElementById('taux-remboursement').value),
            quantiteStock: parseInt(document.getElementById('quantite-stock').value),
            seuilAlerte: parseInt(document.getElementById('seuil-alerte').value),
            datePeremption: document.getElementById('date-peremption').value,
            prescriptionRequise: document.getElementById('prescription-requise').checked
        };

        if (isEditing) {
            medicament.id = currentEditId;
            updateMedicament(medicament);
        } else {
            addMedicament(medicament);
        }
    });

    // Gestion de l'annulation de l'édition
    cancelBtn.addEventListener('click', function() {
        resetForm();
    });

    // Fonction pour charger les médicaments
    function loadMedicaments() {
        fetch('/api/medicaments')
            .then(response => response.json())
            .then(data => {
                const tableBody = document.getElementById('medicaments-table');
                tableBody.innerHTML = '';

                data.forEach(medicament => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${medicament.codeBarre || '-'}</td>
                        <td>${medicament.nom}</td>
                        <td>${medicament.dci || '-'}</td>
                        <td>${medicament.formeGalenique || '-'}</td>
                        <td>${medicament.prixVente.toFixed(2)} €</td>
                        <td class="${medicament.quantiteStock <= medicament.seuilAlerte ? 'text-danger fw-bold' : ''}">
                            ${medicament.quantiteStock}
                            ${medicament.quantiteStock <= medicament.seuilAlerte ? ' (Alerte)' : ''}
                        </td>
                        <td>${medicament.datePeremption ? new Date(medicament.datePeremption).toLocaleDateString() : '-'}</td>
                        <td>
                            <button class="btn btn-sm btn-warning edit-btn me-1" data-id="${medicament.id}">
                                <i class="bi bi-pencil"></i> Modifier
                            </button>
                            <button class="btn btn-sm btn-danger delete-btn" data-id="${medicament.id}">
                                <i class="bi bi-trash"></i> Supprimer
                            </button>
                        </td>
                    `;
                    tableBody.appendChild(row);
                });

                // Ajouter les événements aux boutons
                document.querySelectorAll('.edit-btn').forEach(btn => {
                    btn.addEventListener('click', function() {
                        editMedicament(this.getAttribute('data-id'));
                    });
                });

                document.querySelectorAll('.delete-btn').forEach(btn => {
                    btn.addEventListener('click', function() {
                        deleteMedicament(this.getAttribute('data-id'));
                    });
                });
            })
            .catch(error => console.error('Erreur:', error));
    }

    // Fonction pour ajouter un médicament
    function addMedicament(medicament) {
        fetch('/api/medicaments', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(medicament)
        })
            .then(response => response.json())
            .then(() => {
                resetForm();
                loadMedicaments();
                alert('Médicament ajouté avec succès!');
            })
            .catch(error => {
                console.error('Erreur:', error);
                alert('Erreur lors de l\'ajout du médicament');
            });
    }

    // Fonction pour mettre à jour un médicament
    function updateMedicament(medicament) {
        fetch(`/api/medicaments/${medicament.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(medicament)
        })
            .then(() => {
                resetForm();
                loadMedicaments();
                alert('Médicament mis à jour avec succès!');
            })
            .catch(error => {
                console.error('Erreur:', error);
                alert('Erreur lors de la mise à jour du médicament');
            });
    }

    // Fonction pour supprimer un médicament
    function deleteMedicament(id) {
        if (confirm('Êtes-vous sûr de vouloir supprimer ce médicament?')) {
            fetch(`/api/medicaments/${id}`, {
                method: 'DELETE'
            })
                .then(() => {
                    loadMedicaments();
                    alert('Médicament supprimé avec succès!');
                })
                .catch(error => {
                    console.error('Erreur:', error);
                    alert('Erreur lors de la suppression du médicament');
                });
        }
    }

    // Fonction pour éditer un médicament
    function editMedicament(id) {
        fetch(`/api/medicaments/${id}`)
            .then(response => response.json())
            .then(medicament => {
                document.getElementById('medicament-id').value = medicament.id;
                document.getElementById('code-barre').value = medicament.codeBarre || '';
                document.getElementById('nom').value = medicament.nom;
                document.getElementById('dci').value = medicament.dci || '';
                document.getElementById('dosage').value = medicament.dosage || '';
                document.getElementById('forme-galenique').value = medicament.formeGalenique || '';
                document.getElementById('classe-therapeutique').value = medicament.classeTherapeutique || '';
                document.getElementById('laboratoire').value = medicament.laboratoire || '';
                document.getElementById('prix-achat').value = medicament.prixAchat;
                document.getElementById('prix-vente').value = medicament.prixVente;
                document.getElementById('taux-remboursement').value = medicament.tauxRemboursement;
                document.getElementById('quantite-stock').value = medicament.quantiteStock;
                document.getElementById('seuil-alerte').value = medicament.seuilAlerte;
                document.getElementById('date-peremption').value = medicament.datePeremption ? medicament.datePeremption.split('T')[0] : '';
                document.getElementById('prescription-requise').checked = medicament.prescriptionRequise;

                document.getElementById('form-title').textContent = 'Modifier Médicament';
                cancelBtn.style.display = 'inline-block';
                isEditing = true;
                currentEditId = medicament.id;

                // Scroll to form
                document.querySelector('.form-container').scrollIntoView({ behavior: 'smooth' });
            })
            .catch(error => console.error('Erreur:', error));
    }

    // Fonction pour réinitialiser le formulaire
    function resetForm() {
        form.reset();
        document.getElementById('form-title').textContent = 'Ajouter un Médicament';
        cancelBtn.style.display = 'none';
        isEditing = false;
        currentEditId = null;
        document.getElementById('taux-remboursement').value = '0';
        document.getElementById('quantite-stock').value = '0';
        document.getElementById('seuil-alerte').value = '10';
    }
    function searchMedicaments() {
        const searchTerm = document.getElementById('search-input').value;

        fetch(`/api/medicaments/search?nom=${encodeURIComponent(searchTerm)}`)
            .then(response => response.json())
            .then(data => {
                updateMedicamentsTable(data);
            })
            .catch(error => console.error('Error:', error));
    }

// Ajoutez cette ligne à votre fonction loadMedicaments() si vous voulez une recherche initiale
    function loadMedicaments(searchTerm = '') {
        const url = searchTerm ? `/api/medicaments/search?nom=${encodeURIComponent(searchTerm)}` : '/api/medicaments';

        fetch(url)
            .then(response => response.json())
            .then(data => {
                updateMedicamentsTable(data);
            })
            .catch(error => console.error('Error:', error));
    }
});