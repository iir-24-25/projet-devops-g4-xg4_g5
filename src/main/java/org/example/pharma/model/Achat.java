package org.example.pharma.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "achats")
@Data
public class Achat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_fournisseur", nullable = false)
    private String nomFournisseur;

    @Column(name = "date_achat", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAchat;

    @Column(name = "montant_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_paiement", nullable = false, columnDefinition = "ENUM('Espèces', 'Chèque', 'Virement', 'Carte')")
    private ModePaiement modePaiement;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", columnDefinition = "ENUM('En attente', 'Payé', 'Annulé') DEFAULT 'En attente'")
    private StatutAchat statut = StatutAchat.EN_ATTENTE;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public enum ModePaiement {
        ESPECES("Espèces"),
        CHEQUE("Chèque"),
        VIREMENT("Virement"),
        CARTE("Carte");

        private final String displayValue;

        ModePaiement(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

    public enum StatutAchat {
        EN_ATTENTE("En attente"),
        PAYE("Payé"),
        ANNULE("Annulé");

        private final String displayValue;

        StatutAchat(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }
}