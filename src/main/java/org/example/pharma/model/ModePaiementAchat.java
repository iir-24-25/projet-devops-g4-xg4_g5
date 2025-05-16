package org.example.pharma.model;

public enum ModePaiementAchat {
    ESPECES("Espèces"),
    CHEQUE("Chèque"),
    VIREMENT("Virement"),
    CARTE("Carte");

    private final String displayName;

    ModePaiementAchat(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}