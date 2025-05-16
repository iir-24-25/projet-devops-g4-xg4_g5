package org.example.pharma.model;

public enum StatutAchat {
    EN_ATTENTE("En attente"),
    PAYE("Payé"),
    ANNULE("Annulé");

    private final String displayName;

    StatutAchat(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}