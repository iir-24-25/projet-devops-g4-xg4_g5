package org.example.pharma.model;

public enum ModePaiement {
    CARTE("Carte bancaire"),
    ESPECES("Espèces"),
    VIREMENT("Virement bancaire"),
    CHEQUE("Chèque"),
    AUTRE("Autre moyen");

    private final String libelle;

    ModePaiement(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}