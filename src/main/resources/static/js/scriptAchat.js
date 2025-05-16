// Variables globales
let achatModal = new bootstrap.Modal(document.getElementById('achatModal'));
let confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
let currentAchatId = null;

// Chargement initial des achats
document.addEventListener('DOMContentLoaded', function() {
    loadAchats();

    // Gestion du formulaire
    document.getElementById('achatForm').addEventListener('submit', handleFormSubmit);

    // Bouton nouveau achat
    document.getElementById('btnNouveauAchat').addEventListener('click', showNewAchatForm);
});

// Charger la liste des achats
function loadAchats() {
    showLoading(true);
    fetch('/api/achats')
        .then(response => response.json())
        .then(achats => {
            const tableBody = document.getElementById('achatsTableBody');
            tableBody.innerHTML = '';

            if (achats.length === 0) {
                tableBody.innerHTML = '<tr><td colspan="7" class="text-center">Aucun achat enregistré</td></tr>';
                return;
            }

            achats.forEach(achat => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${achat.id}</td>
                    <td>${achat.nomFournisseur}</td>
                    <td>${formatDate(achat.dateAchat)}</td>
                    <td>${achat.montantTotal.toFixed(2)} €</td>
                    <td>${translateModePaiement(achat.modePaiement)}</td>
                    <td><span class="badge ${getStatutClass(achat.statut)}">${translateStatut(achat.statut)}</span></td>
                    <td class="action-buttons">
                        <button class="btn btn-sm btn-warning edit-btn" data-id="${achat.id}">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button class="btn btn-sm btn-danger delete-btn" data-id="${achat.id}">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                `;
                tableBody.appendChild(row);
            });

            // Ajouter les événements aux boutons
            document.querySelectorAll('.edit-btn').forEach(btn => {
                btn.addEventListener('click', function() {
                    showEditForm(this.getAttribute('data-id'));
                });
            });

            document.querySelectorAll('.delete-btn').forEach(btn => {
                btn.addEventListener('click', function() {
                    showDeleteConfirm(this.getAttribute('data-id'));
                });
            });
        })
        .catch(error => {
            console.error('Erreur:', error);
            alert('Erreur lors du chargement des achats');
        })
        .finally(() => showLoading(false));
}

// Afficher le formulaire pour un nouvel achat
function showNewAchatForm() {
    document.getElementById('modalTitle').textContent = 'Nouvel Achat';
    document.getElementById('achatForm').reset();
    document.getElementById('achatId').value = '';
    document.getElementById('statut').value = 'EN_ATTENTE';
    currentAchatId = null;
    achatModal.show();
}

// Afficher le formulaire pour modifier un achat
function showEditForm(id) {
    showLoading(true);
    fetch(`/api/achats/${id}`)
        .then(response => response.json())
        .then(achat => {
            document.getElementById('modalTitle').textContent = 'Modifier Achat';
            document.getElementById('achatId').value = achat.id;
            document.getElementById('nomFournisseur').value = achat.nomFournisseur;
            document.getElementById('dateAchat').value = formatDateForInput(achat.dateAchat);
            document.getElementById('montantTotal').value = achat.montantTotal;
            document.getElementById('modePaiement').value = achat.modePaiement;
            document.getElementById('statut').value = achat.statut;
            document.getElementById('notes').value = achat.notes || '';
            currentAchatId = achat.id;
            achatModal.show();
        })
        .catch(error => {
            console.error('Erreur:', error);
            alert('Erreur lors du chargement de l\'achat');
        })
        .finally(() => showLoading(false));
}

// Gérer la soumission du formulaire
function handleFormSubmit(e) {
    e.preventDefault();

    const achatData = {
        nomFournisseur: document.getElementById('nomFournisseur').value,
        dateAchat: document.getElementById('dateAchat').value,
        montantTotal: parseFloat(document.getElementById('montantTotal').value),
        modePaiement: document.getElementById('modePaiement').value,
        statut: document.getElementById('statut').value,
        notes: document.getElementById('notes').value
    };

    showLoading(true);

    const url = currentAchatId ? `/api/achats/${currentAchatId}` : '/api/achats';
    const method = currentAchatId ? 'PUT' : 'POST';

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(achatData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erreur lors de l\'enregistrement');
            }
            return response.json();
        })
        .then(() => {
            achatModal.hide();
            loadAchats();
            alert('Achat enregistré avec succès');
        })
        .catch(error => {
            console.error('Erreur:', error);
            alert('Erreur lors de l\'enregistrement de l\'achat');
        })
        .finally(() => showLoading(false));
}

// Afficher la confirmation de suppression
function showDeleteConfirm(id) {
    currentAchatId = id;
    confirmDeleteModal.show();

    // Gérer la confirmation de suppression
    document.getElementById('confirmDeleteBtn').onclick = function() {
        deleteAchat(id);
        confirmDeleteModal.hide();
    };
}

// Supprimer un achat
function deleteAchat(id) {
    showLoading(true);
    fetch(`/api/achats/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erreur lors de la suppression');
            }
            loadAchats();
            alert('Achat supprimé avec succès');
        })
        .catch(error => {
            console.error('Erreur:', error);
            alert('Erreur lors de la suppression de l\'achat');
        })
        .finally(() => showLoading(false));
}

// Fonctions utilitaires
function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('fr-FR') + ' ' + date.toLocaleTimeString('fr-FR');
}

function formatDateForInput(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    const offset = date.getTimezoneOffset() * 60000;
    return new Date(date.getTime() - offset).toISOString().slice(0, 16);
}

function translateModePaiement(mode) {
    const translations = {
        'ESPECES': 'Espèces',
        'CHEQUE': 'Chèque',
        'VIREMENT': 'Virement',
        'CARTE': 'Carte'
    };
    return translations[mode] || mode;
}

function translateStatut(statut) {
    const translations = {
        'EN_ATTENTE': 'En attente',
        'PAYE': 'Payé',
        'ANNULE': 'Annulé'
    };
    return translations[statut] || statut;
}

function getStatutClass(statut) {
    const classes = {
        'PAYE': 'bg-paye',
        'EN_ATTENTE': 'bg-attente',
        'ANNULE': 'bg-annule'
    };
    return classes[statut] || 'bg-secondary';
}

function showLoading(show) {
    document.getElementById('loading').style.display = show ? 'flex' : 'none';
}