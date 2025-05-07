document.addEventListener('DOMContentLoaded', function() {
    // Initialisation des éléments
    const venteForm = document.getElementById('venteForm');
    const ventesTableBody = document.getElementById('ventesTableBody');
    const searchInput = document.getElementById('searchInput');
    const filterStatut = document.getElementById('filterStatut');
    const filterDateFrom = document.getElementById('filterDateFrom');
    const filterDateTo = document.getElementById('filterDateTo');
    const saveVenteBtn = document.getElementById('saveVenteBtn');
    const exportBtn = document.getElementById('exportBtn');
    const importBtn = document.getElementById('importBtn');
    const confirmImportBtn = document.getElementById('confirmImportBtn');
    const addVenteModal = new bootstrap.Modal(document.getElementById('addVenteModal'));
    const importModal = new bootstrap.Modal(document.getElementById('importModal'));

    let currentSort = { column: 'id', direction: 'asc' };
    let ventesData = [];

    // Charger toutes les ventes au démarrage
    fetchVentes();

    // Gestion de la soumission du formulaire
    saveVenteBtn.addEventListener('click', function() {
        const venteId = document.getElementById('venteId').value;
        const venteData = {
            client: { id: parseInt(document.getElementById('clientId').value) },
            montantTotal: parseFloat(document.getElementById('montantTotal').value),
            statut: document.getElementById('statut').value,
            modePaiement: document.getElementById('modePaiement').value,
            reference: document.getElementById('reference').value,
            notes: document.getElementById('notes').value,
            dateVente: document.getElementById('dateVente').value || new Date().toISOString()
        };

        const method = venteId ? 'PUT' : 'POST';
        const url = venteId ? `/api/ventes/${venteId}` : '/api/ventes';

        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(venteData)
        })
            .then(response => response.json())
            .then(data => {
                fetchVentes();
                addVenteModal.hide();
                venteForm.reset();
            })
            .catch(error => console.error('Error:', error));
    });

    // Recherche et filtrage
    searchInput.addEventListener('input', applyFilters);
    filterStatut.addEventListener('change', applyFilters);
    filterDateFrom.addEventListener('change', applyFilters);
    filterDateTo.addEventListener('change', applyFilters);

    // Tri des colonnes
    document.querySelectorAll('th[data-sort]').forEach(header => {
        header.addEventListener('click', () => {
            const column = header.getAttribute('data-sort');
            if (currentSort.column === column) {
                currentSort.direction = currentSort.direction === 'asc' ? 'desc' : 'asc';
            } else {
                currentSort.column = column;
                currentSort.direction = 'asc';
            }
            applyFilters();
        });
    });

    // Export des données
    exportBtn.addEventListener('click', exportData);

    // Import des données
    importBtn.addEventListener('click', () => importModal.show());
    confirmImportBtn.addEventListener('click', importData);

    // Fonction pour charger les ventes
    function fetchVentes() {
        fetch('/api/ventes')
            .then(response => response.json())
            .then(data => {
                ventesData = data;
                applyFilters();
            })
            .catch(error => console.error('Error:', error));
    }

    // Appliquer les filtres et le tri
    function applyFilters() {
        const searchTerm = searchInput.value.toLowerCase();
        const statutFilter = filterStatut.value;
        const dateFrom = filterDateFrom.value;
        const dateTo = filterDateTo.value;

        let filteredVentes = ventesData.filter(vente => {
            const matchesSearch =
                vente.id.toString().includes(searchTerm) ||
                vente.client.id.toString().includes(searchTerm) ||
                (vente.reference && vente.reference.toLowerCase().includes(searchTerm)) ||
                (vente.notes && vente.notes.toLowerCase().includes(searchTerm));

            const matchesStatut = !statutFilter || vente.statut === statutFilter;

            const venteDate = new Date(vente.dateVente);
            const matchesDateFrom = !dateFrom || new Date(dateFrom) <= venteDate;
            const matchesDateTo = !dateTo || new Date(dateTo + 'T23:59:59') >= venteDate;

            return matchesSearch && matchesStatut && matchesDateFrom && matchesDateTo;
        });

        // Tri des données
        filteredVentes.sort((a, b) => {
            let valA, valB;

            if (currentSort.column.includes('.')) {
                const parts = currentSort.column.split('.');
                valA = a[parts[0]][parts[1]];
                valB = b[parts[0]][parts[1]];
            } else {
                valA = a[currentSort.column];
                valB = b[currentSort.column];
            }

            if (typeof valA === 'string') valA = valA.toLowerCase();
            if (typeof valB === 'string') valB = valB.toLowerCase();

            if (valA < valB) return currentSort.direction === 'asc' ? -1 : 1;
            if (valA > valB) return currentSort.direction === 'asc' ? 1 : -1;
            return 0;
        });

        renderTable(filteredVentes);
    }

    // Afficher les données dans le tableau
    function renderTable(ventes) {
        ventesTableBody.innerHTML = '';

        ventes.forEach(vente => {
            const row = document.createElement('tr');

            row.innerHTML = `
                <td>${vente.id}</td>
                <td>${vente.client.id}</td>
                <td>${new Date(vente.dateVente).toLocaleString()}</td>
                <td>${vente.montantTotal.toFixed(2)}</td>
                <td><span class="badge ${getStatusBadgeClass(vente.statut)}">${getStatusDisplayName(vente.statut)}</span></td>
                <td>${getPaymentDisplayName(vente.modePaiement)}</td>
                <td>
                    <button class="btn btn-sm btn-warning edit-btn me-1" data-id="${vente.id}">
                        <i class="bi bi-pencil"></i> Modifier
                    </button>
                    <button class="btn btn-sm btn-danger delete-btn" data-id="${vente.id}">
                        <i class="bi bi-trash"></i> Supprimer
                    </button>
                </td>
            `;

            ventesTableBody.appendChild(row);
        });

        // Ajouter les événements aux boutons
        document.querySelectorAll('.edit-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                const venteId = this.getAttribute('data-id');
                editVente(venteId);
            });
        });

        document.querySelectorAll('.delete-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                const venteId = this.getAttribute('data-id');
                deleteVente(venteId);
            });
        });
    }

    // Fonction pour éditer une vente
    function editVente(id) {
        fetch(`/api/ventes/${id}`)
            .then(response => response.json())
            .then(vente => {
                document.getElementById('venteId').value = vente.id;
                document.getElementById('clientId').value = vente.client.id;
                document.getElementById('montantTotal').value = vente.montantTotal;
                document.getElementById('statut').value = vente.statut;
                document.getElementById('modePaiement').value = vente.modePaiement;
                document.getElementById('reference').value = vente.reference || '';
                document.getElementById('notes').value = vente.notes || '';

                // Formater la date pour l'input datetime-local
                const date = new Date(vente.dateVente);
                const formattedDate = date.toISOString().slice(0, 16);
                document.getElementById('dateVente').value = formattedDate;

                document.getElementById('modalTitle').textContent = 'Modifier Vente';
                addVenteModal.show();
            })
            .catch(error => console.error('Error:', error));
    }

    // Fonction pour supprimer une vente
    function deleteVente(id) {
        if (confirm('Êtes-vous sûr de vouloir supprimer cette vente ?')) {
            fetch(`/api/ventes/${id}`, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        fetchVentes();
                    }
                })
                .catch(error => console.error('Error:', error));
        }
    }

    // Fonction pour exporter les données
    function exportData() {
        const data = ventesData.map(vente => ({
            ID: vente.id,
            'Client ID': vente.client.id,
            Date: new Date(vente.dateVente).toLocaleString(),
            Montant: vente.montantTotal,
            Statut: getStatusDisplayName(vente.statut),
            Paiement: getPaymentDisplayName(vente.modePaiement),
            Référence: vente.reference || '',
            Notes: vente.notes || ''
        }));

        const ws = XLSX.utils.json_to_sheet(data);
        const wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, ws, "Ventes");
        XLSX.writeFile(wb, "export_ventes.xlsx");
    }

    // Fonction pour importer des données
    function importData() {
        const fileInput = document.getElementById('importFile');
        const file = fileInput.files[0];

        if (!file) {
            alert('Veuillez sélectionner un fichier');
            return;
        }

        const reader = new FileReader();
        reader.onload = function(e) {
            const data = new Uint8Array(e.target.result);
            const workbook = XLSX.read(data, { type: 'array' });
            const firstSheet = workbook.Sheets[workbook.SheetNames[0]];
            const jsonData = XLSX.utils.sheet_to_json(firstSheet);

            // Transformer les données pour correspondre à notre modèle
            const ventesToImport = jsonData.map(item => ({
                client: { id: item['Client ID'] },
                montantTotal: item.Montant,
                statut: getStatusKey(item.Statut),
                modePaiement: getPaymentKey(item.Paiement),
                reference: item.Référence || '',
                notes: item.Notes || '',
                dateVente: new Date(item.Date).toISOString()
            }));

            // Envoyer les données au serveur
            fetch('/api/ventes/import', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(ventesToImport)
            })
                .then(response => {
                    if (response.ok) {
                        importModal.hide();
                        fetchVentes();
                        alert('Importation réussie !');
                    } else {
                        throw new Error('Erreur lors de l\'importation');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Erreur lors de l\'importation');
                });
        };
        reader.readAsArrayBuffer(file);
    }

    // Helper functions
    function getStatusDisplayName(status) {
        switch(status) {
            case 'EN_ATTENTE': return 'En attente';
            case 'PAYEE': return 'Payée';
            case 'ANNULEE': return 'Annulée';
            case 'LIVREE': return 'Livrée';
            default: return status;
        }
    }

    function getStatusKey(displayName) {
        switch(displayName) {
            case 'En attente': return 'EN_ATTENTE';
            case 'Payée': return 'PAYEE';
            case 'Annulée': return 'ANNULEE';
            case 'Livrée': return 'LIVREE';
            default: return displayName;
        }
    }

    function getPaymentDisplayName(payment) {
        switch(payment) {
            case 'CARTE': return 'Carte';
            case 'ESPECES': return 'Espèces';
            case 'VIREMENT': return 'Virement';
            case 'CHEQUE': return 'Chèque';
            case 'AUTRE': return 'Autre';
            default: return payment;
        }
    }

    function getPaymentKey(displayName) {
        switch(displayName) {
            case 'Carte': return 'CARTE';
            case 'Espèces': return 'ESPECES';
            case 'Virement': return 'VIREMENT';
            case 'Chèque': return 'CHEQUE';
            case 'Autre': return 'AUTRE';
            default: return displayName;
        }
    }

    function getStatusBadgeClass(status) {
        switch(status) {
            case 'EN_ATTENTE': return 'bg-warning text-dark';
            case 'PAYEE': return 'bg-success';
            case 'ANNULEE': return 'bg-danger';
            case 'LIVREE': return 'bg-primary';
            default: return 'bg-secondary';
        }
    }
});