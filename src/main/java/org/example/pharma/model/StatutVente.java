package org.example.pharma.model;

public enum StatutVente {
    EN_ATTENTE("En attente"),
    PAYEE("Payée"),
    ANNULEE("Annulée"),
    LIVREE("Livrée");

    private final String libelle;

    StatutVente(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}